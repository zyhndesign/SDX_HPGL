package com.cidic.sdx.dao.impl;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cidic.sdx.dao.UserDao;
import com.cidic.sdx.model.UserModel;
import com.cidic.sdx.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "userDaoImpl")
public class UserImpl implements UserDao {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	private final String USER_ID_USERNAME_MAP = RedisVariableUtil.USER_PRIFIX+"_ID_MAP";
	private final String USER_ROLE_LIST = RedisVariableUtil.USER_PRIFIX+":";
	
	@Override
	public UserModel createUser(UserModel user) {
		return redisTemplate.execute(new RedisCallback<UserModel>() {
			@Override
			public UserModel doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				long id = connection.incr(ser.serialize(RedisVariableUtil.USER_PRIFIX + "Id"));
				user.setId((int) id);
				byte[] roleKey = ser.serialize(RedisVariableUtil.USER_PRIFIX + ":" + id);
				connection.openPipeline();
				connection.hSet(roleKey, ser.serialize("username"), ser.serialize(user.getUsername()));
				connection.hSet(roleKey, ser.serialize("password"), ser.serialize(user.getPassword()));
				connection.hSet(roleKey, ser.serialize("slot"), ser.serialize(user.getSlot()));
				connection.hSet(roleKey, ser.serialize("locked"), ser.serialize(String.valueOf(user.isLocked())));
				
				connection.hSet(ser.serialize(USER_ID_USERNAME_MAP), ser.serialize(user.getUsername()), ser.serialize(String.valueOf(id)));
				if (user.getRoleList() != null){
					user.getRoleList().stream().forEach((i)->{
						connection.sAdd(ser.serialize(USER_ROLE_LIST + id + ":" + RedisVariableUtil.ROLE_PRIFIX), ser.serialize(String.valueOf(i)));
					});;
				}
				connection.closePipeline();
				return user;
			}
		});
	}

	@Override
	public void updateUser(UserModel user) {
	    redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				byte[] roleKey = ser.serialize(RedisVariableUtil.USER_PRIFIX + ":" + user.getId());
				connection.openPipeline();
				connection.hSet(roleKey, ser.serialize("username"), ser.serialize(user.getUsername()));
				connection.hSet(roleKey, ser.serialize("password"), ser.serialize(user.getPassword()));
				connection.hSet(roleKey, ser.serialize("slot"), ser.serialize(user.getSlot()));
				connection.hSet(roleKey, ser.serialize("locked"), ser.serialize(String.valueOf(user.isLocked())));
				connection.closePipeline();
				return null;
			}
		});
	}

	@Override
	public void deleteUser(Long userId) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				byte[] roleKey = ser.serialize(RedisVariableUtil.USER_PRIFIX + ":" + userId);
				byte[] username = connection.hGet(roleKey, ser.serialize("username"));
				connection.openPipeline();
				connection.hDel(roleKey, ser.serialize("username"), ser.serialize("password"),ser.serialize("slot"),ser.serialize("locked"));
				connection.del(ser.serialize(USER_ROLE_LIST+ser.deserialize(username)));
				connection.hDel(ser.serialize(USER_ID_USERNAME_MAP), username);
				connection.closePipeline();
				return null;
			}
		});
	}

	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] roleKey = ser
						.serialize(RedisVariableUtil.USER_PRIFIX + ":" + userId + ":" + RedisVariableUtil.ROLE_PRIFIX);
				connection.openPipeline();
				for (long id : roleIds) {
					connection.sAdd(roleKey, ser.serialize(String.valueOf(id)));
				}

				connection.closePipeline();

				return null;
			}
		});
	}

	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] roleKey = ser
						.serialize(RedisVariableUtil.USER_PRIFIX + ":" + userId + ":" + RedisVariableUtil.ROLE_PRIFIX);
				connection.openPipeline();
				for (long id : roleIds) {
					connection.sRem(roleKey, ser.serialize(String.valueOf(id)));
				}
				connection.closePipeline();

				return null;
			}
		});
	}

	@Override
	public UserModel findOne(Long userId) {
		return redisTemplate.execute(new RedisCallback<UserModel>() {
			@Override
			public UserModel doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				byte[] roleKey = ser.serialize(RedisVariableUtil.USER_PRIFIX + ":" + userId);
				Map<byte[], byte[]> userModelMap = connection.hGetAll(roleKey);
				UserModel userModel = new UserModel();
				userModelMap.forEach((k,v)->{
					if (k.equals("username")){
						userModel.setUsername(ser.deserialize(v));
					}
					else if (k.equals("password")){
						userModel.setPassword(ser.deserialize(v));
					}
					else if (k.equals("slot")){
						userModel.setSlot(ser.deserialize(v));
					}
					else if (k.equals("locked")){
						userModel.setLocked(Boolean.parseBoolean(ser.deserialize(v)));
					}
				});
				return userModel;
			}
		});
	}

	@Override
	public UserModel findByUsername(String username) {
		return redisTemplate.execute(new RedisCallback<UserModel>() {
			@Override
			public UserModel doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] bId = connection.hGet(ser.serialize(USER_ID_USERNAME_MAP), ser.serialize(username));
				if (bId != null){
					byte[] roleKey = ser.serialize(RedisVariableUtil.USER_PRIFIX + ":" + ser.deserialize(bId));
					Map<byte[], byte[]> userModelMap = connection.hGetAll(roleKey);
					UserModel userModel = new UserModel();
					userModelMap.forEach((k,v)->{
						if (ser.deserialize(k).equals("username")){
							userModel.setUsername(ser.deserialize(v));
						}
						else if (ser.deserialize(k).equals("password")){
							userModel.setPassword(ser.deserialize(v));
						}
						else if (ser.deserialize(k).equals("slot")){
							userModel.setSlot(ser.deserialize(v));
						}
						else if (ser.deserialize(k).equals("locked")){
							userModel.setLocked(Boolean.parseBoolean(ser.deserialize(v)));
						}
					});
					return userModel;
				}
				else{
					return null;
				}
			}
		});
	}

	@Override
	public Set<String> findRoles(String username) {
		
		return redisTemplate.execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] id = connection.hGet(ser.serialize(USER_ID_USERNAME_MAP), ser.serialize(username));
				Set<byte[]> idSet= connection.sMembers(ser.serialize(USER_ROLE_LIST + ser.deserialize(id) + ":" + RedisVariableUtil.ROLE_PRIFIX));
				Set<String> result = new TreeSet<>();
				idSet.stream().forEach((ids)->{
					result.add(ser.deserialize(connection.hGet(ser.serialize(RedisVariableUtil.ROLE_PRIFIX + ":" + ser.deserialize(ids)), ser.serialize("role"))));
				});
				return result;
			}
		});
	}

	@Override
	public Set<String> findPermissions(String username) {
		
		return redisTemplate.execute(new RedisCallback<Set<String>>() {
			@Override
			public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] id = connection.hGet(ser.serialize(USER_ID_USERNAME_MAP), ser.serialize(username));
				Set<byte[]> roleIdSet= connection.sMembers(ser.serialize(USER_ROLE_LIST + ser.deserialize(id) + ":" + RedisVariableUtil.ROLE_PRIFIX));
				Set<String> result = new TreeSet<>();
				roleIdSet.stream().forEach((roleId)->{
					Set<byte[]> permissiomIdSet =  connection.sMembers(ser.serialize(RedisVariableUtil.ROLE_PRIFIX + ":" + ser.deserialize(roleId) +":"+ RedisVariableUtil.PERMISSION_PRIFIX));
					permissiomIdSet.stream().forEach((permissiomId)->{
						result.add(ser.deserialize(connection.hGet(ser.serialize(RedisVariableUtil.PERMISSION_PRIFIX + ":" + ser.deserialize(permissiomId)), ser.serialize("permission"))));
					});
				});
				return result;
			}
		});
	}

}
