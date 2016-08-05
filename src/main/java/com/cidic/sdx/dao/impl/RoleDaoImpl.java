package com.cidic.sdx.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cidic.sdx.dao.RoleDao;
import com.cidic.sdx.model.RoleModel;
import com.cidic.sdx.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "roleDaoImpl")
public class RoleDaoImpl implements RoleDao {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public RoleModel createRole(RoleModel role) {

		return redisTemplate.execute(new RedisCallback<RoleModel>() {
			@Override
			public RoleModel doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				long id = connection.incr(ser.serialize(RedisVariableUtil.ROLE_PRIFIX + "Id"));
				role.setId((int) id);
				byte[] roleKey = ser.serialize(RedisVariableUtil.ROLE_PRIFIX + ":" + id);
				connection.openPipeline();
				connection.hSet(roleKey, ser.serialize("role"), ser.serialize(role.getRole()));
				connection.hSet(roleKey, ser.serialize("description"), ser.serialize(role.getDescription()));
				connection.closePipeline();
				return role;
			}
		});
	}

	@Override
	public void deleteRole(Long roleId) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] roleKey = ser.serialize(RedisVariableUtil.ROLE_PRIFIX + ":" + roleId);
				connection.hDel(roleKey, ser.serialize("role"), ser.serialize("description"));
				return null;
			}
		});
	}

	@Override
	public void correlationPermissions(Long roleId, Long... permissionIds) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] roleKey = ser
						.serialize(RedisVariableUtil.ROLE_PRIFIX + ":" + roleId +":"+ RedisVariableUtil.PERMISSION_PRIFIX);
				connection.openPipeline();
				for (long id : permissionIds) {
					connection.sAdd(roleKey, ser.serialize(String.valueOf(id)));
				}

				connection.closePipeline();

				return null;
			}
		});
	}

	@Override
	public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] roleKey = ser
						.serialize(RedisVariableUtil.ROLE_PRIFIX + ":" + roleId +":"+ RedisVariableUtil.PERMISSION_PRIFIX);
				connection.openPipeline();
				for (long id : permissionIds) {
					connection.sRem(roleKey, ser.serialize(String.valueOf(id)));
				}
				connection.closePipeline();

				return null;
			}
		});
	}

}
