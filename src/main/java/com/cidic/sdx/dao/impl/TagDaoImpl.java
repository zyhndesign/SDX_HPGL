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

import com.cidic.sdx.dao.TagDao;

@Repository
@Component
@Qualifier(value = "tagDaoImpl")
public class TagDaoImpl implements TagDao {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void insertTag(String key, String value) {
		
	   redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				connection.sAdd(ser.serialize(key), ser.serialize(value));
				return null;
			}
		});
	}

	@Override
	public void updateTag(String key, String old_value, String new_value) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				connection.multi();
				connection.sRem(ser.serialize(key), ser.serialize(old_value));
				connection.sAdd(ser.serialize(key), ser.serialize(new_value));
				connection.exec();
				return null;
			}
		});
	}

}
