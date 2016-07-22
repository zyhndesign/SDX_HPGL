package com.cidic.sdx.dao.impl;

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
@Qualifier(value = "boardDaoImpl")
public class BrandDaoImpl implements BrandDao {

	@Autowired
	@Qualifier(value="redisTemplate")
	private RedisTemplate<String,String> redisTemplate;

	@Override
	public Map<String, String> getBrandDateByKey(String key) {
		HashOperations<String,String,String>  valueOperations = redisTemplate.opsForHash();
		return valueOperations.entries(key);
	}
 
	@Override
	public long insertBrandData(String key, String value) {
		String id_key = RedisVariableUtil.BOARD_PREFIX + "Id";
		
	    redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
               
                RedisSerializer<String> ser = redisTemplate.getStringSerializer();
                connection.multi();
                byte[] bIdKey = ser.serialize(id_key);
                if (!connection.exists(bIdKey)) {
                	connection.set(ser.serialize(id_key), ser.serialize("0"));
                }
               
    		    long id = connection.incr(ser.serialize(id_key));
    		    connection.hSet(ser.serialize(key), ser.serialize(RedisVariableUtil.BOARD_PREFIX+"."+id), ser.serialize(value));
    		    connection.exec();
                return id;
            }
        });
		return 0;
	}

	@Override
	public void updateBrandData(String parentKey,String key, String value) {
		 redisTemplate.execute(new RedisCallback<Object>() {
	            @Override
	            public Object doInRedis(RedisConnection connection) throws DataAccessException {
	            	RedisSerializer<String> ser = redisTemplate.getStringSerializer();
	            	connection.hSet(ser.serialize(RedisVariableUtil.BOARD_PREFIX+"."+parentKey), ser.serialize(RedisVariableUtil.BOARD_PREFIX+"."+key), ser.serialize(value));
	            	return null;
	            }
		 });
		
	}

	@Override
	public void deleteBrandData(String parentKey,String key) {
		redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
            	RedisSerializer<String> ser = redisTemplate.getStringSerializer();
            	connection.hDel(ser.serialize(RedisVariableUtil.BOARD_PREFIX+"."+parentKey), ser.serialize(RedisVariableUtil.BOARD_PREFIX+"."+key));
            	return null;
            }
		});
		
	}
}
