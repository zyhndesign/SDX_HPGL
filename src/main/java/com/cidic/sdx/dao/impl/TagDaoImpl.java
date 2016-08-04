package com.cidic.sdx.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.util.RedisVariableUtil;

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
				String[] insertId = value.split("\\,");
				for (String id : insertId){
					connection.sAdd(ser.serialize(key), ser.serialize(id));
				}
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
				String[] delId = old_value.split("\\,");
				for (String id : delId){
					connection.sRem(ser.serialize(key), ser.serialize(id));
				} 
				
				String[] newId = new_value.split("\\,");
				for (String id : newId){
					connection.sAdd(ser.serialize(key), ser.serialize(id));
				}
				
				String[] tempKey = key.split("\\:");
				connection.hSet(ser.serialize("hp:"+tempKey[1]), ser.serialize(tempKey[0].substring(4, tempKey[0].length())), ser.serialize(new_value));
				
				connection.exec();
				return null;
			}
		});
	}

	@Override
	public List<Map<String, String>> getAllTag() {
		return redisTemplate.execute(new RedisCallback<List<Map<String, String>>>() {
			@Override
			public  List<Map<String, String>> doInRedis(RedisConnection connection) throws DataAccessException {
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				Map<byte[],byte[]> brandMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> categoryMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.CATEGORY_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> colorMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.COLOR_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> sizeMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.SIZE_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				
				Map<String, String> resultBrandMap = new HashMap<>();
				brandMapList.forEach((k, v) -> {
					resultBrandMap.put(ser.deserialize(k), ser.deserialize(v));
				});
				list.add(resultBrandMap);
				
				Map<String, String> resultCategoryMap = new HashMap<>();
				categoryMapList.forEach((k, v) -> {
					resultCategoryMap.put(ser.deserialize(k), ser.deserialize(v));
				});
				list.add(resultCategoryMap);
				
				Map<String, String> resultColorMap = new HashMap<>();
				colorMapList.forEach((k, v) -> {
					resultColorMap.put(ser.deserialize(k), ser.deserialize(v));
				});
				list.add(resultColorMap);
				
				Map<String, String> resultSizeMap = new HashMap<>();
				sizeMapList.forEach((k, v) -> {
					resultSizeMap.put(ser.deserialize(k), String.valueOf(ser.deserialize(v).charAt(0)));
				});
				list.add(resultSizeMap);
				return list;
			}
		});
	}

}
