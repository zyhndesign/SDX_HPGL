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

import com.cidic.sdx.dao.PermissionDao;
import com.cidic.sdx.model.PermissionsModel;
import com.cidic.sdx.model.RoleModel;
import com.cidic.sdx.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "permissionsDaoImpl")
public class PermissionsDaoImpl implements PermissionDao {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public PermissionsModel createPermission(PermissionsModel permission) {
		return redisTemplate.execute(new RedisCallback<PermissionsModel>() {
			@Override
			public PermissionsModel doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				long id = connection.incr(ser.serialize(RedisVariableUtil.PERMISSION_PRIFIX + "Id"));
				permission.setId((int)id);
				byte[] roleKey = ser.serialize(RedisVariableUtil.PERMISSION_PRIFIX +":"+id);
				connection.openPipeline();
				connection.hSet(roleKey, ser.serialize("permission"), ser.serialize(permission.getPermission()));
				connection.hSet(roleKey, ser.serialize("description"), ser.serialize(permission.getDescription()));
				connection.closePipeline();
				return permission;
			}
		});
	}

	@Override
	public void deletePermission(Long permissionId) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] roleKey = ser.serialize(RedisVariableUtil.PERMISSION_PRIFIX +":"+permissionId);
				connection.hDel(roleKey, ser.serialize("permission"),ser.serialize("description"));
				return null;
			}
		});
	}

}
