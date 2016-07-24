package com.cidic.sdx.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cidic.sdx.dao.BrandDao;
import com.cidic.sdx.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "brandDaoImpl")
public class BrandDaoImpl implements BrandDao {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public Map<String, String> getBrandDataByKey(String key) {

		return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
			@Override
			public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				Map<byte[], byte[]> map = connection.hGetAll(ser.serialize(key));
				Map<String, String> resultMap = new HashMap<>();
				map.forEach((k, v) -> {
					resultMap.put(ser.deserialize(k), ser.deserialize(v));
				});
				return resultMap;
			}
		});

	}

	@Override
	public long insertBrandData(String key, String value) {

		String id_key = RedisVariableUtil.BRAND_PREFIX + "Id";

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();

				byte[] bIdKey = ser.serialize(id_key);

				long id = connection.incr(bIdKey);
				connection.hSet(ser.serialize(RedisVariableUtil.BRAND_PREFIX + "." + key),
						ser.serialize(RedisVariableUtil.BRAND_PREFIX + "." + id), ser.serialize(value));

				return id;
			}
		});
	}

	@Override
	public void updateBrandData(String parentKey, String key, String value) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				connection.hSet(ser.serialize(RedisVariableUtil.BRAND_PREFIX + "." + parentKey),
						ser.serialize(RedisVariableUtil.BRAND_PREFIX + "." + key), ser.serialize(value));
				return null;
			}
		});

	}

	@Override
	public void deleteBrandData(String parentKey, String key) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				connection.hDel(ser.serialize(RedisVariableUtil.BRAND_PREFIX + "." + parentKey),
						ser.serialize(RedisVariableUtil.BRAND_PREFIX + "." + key));
				return null;
			}
		});

	}
}