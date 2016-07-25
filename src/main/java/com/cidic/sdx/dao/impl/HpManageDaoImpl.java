package com.cidic.sdx.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cidic.sdx.dao.HpManageDao;
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "hpManageDaoImpl")
public class HpManageDaoImpl implements HpManageDao {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void insertHpData(HPModel hpModel) {
		String id_key = RedisVariableUtil.HP_RECORD_PREFIX + "Id";

	    redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();

				byte[] bIdKey = ser.serialize(id_key);

				long id = connection.incr(bIdKey);
				
				connection.multi();
				//将记录Id存入队列
				connection.lPush(bIdKey, ser.serialize(String.valueOf(id)));
				//将记录Id存入哈希表
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("hp_num"), ser.serialize(hpModel.getHp_num()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("brand"), ser.serialize(hpModel.getBrand()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("category"), ser.serialize(hpModel.getCategory()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("size"), ser.serialize(hpModel.getSize()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("color"), ser.serialize(hpModel.getColor()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("price"), ser.serialize(String.valueOf(hpModel.getPrice())));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("imageUrl1"), ser.serialize(hpModel.getImageUrl1()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("imageUrl2"), ser.serialize(hpModel.getImageUrl2()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("imageUrl3"), ser.serialize(hpModel.getImageUrl3()));
				
				String[] brandArray = hpModel.getBrand().split("\\,");
				for (String s : brandArray){
					String key = RedisVariableUtil.BRAND_TAG_PREFIX + "." +s;
					connection.sAdd(ser.serialize(key), ser.serialize(String.valueOf(id)));
				}
				
				String[] categoryArray = hpModel.getCategory().split("\\,");
				for (String s : categoryArray){
					String key = RedisVariableUtil.CATEGORY_TAG_PREFIX + "." +s;
					connection.sAdd(ser.serialize(key), ser.serialize(String.valueOf(id)));
				}

				String[] sizeArray = hpModel.getSize().split("\\,");
				for (String s : sizeArray){
					String key = RedisVariableUtil.COLOR_TAG_PREFIX + "." +s;
					connection.sAdd(ser.serialize(key), ser.serialize(String.valueOf(id)));
				}

				String[] colorArray = hpModel.getColor().split("\\,");
				for (String s : colorArray){
					String key = RedisVariableUtil.SIZE_TAG_PREFIX + "." +s;
					connection.sAdd(ser.serialize(key), ser.serialize(String.valueOf(id)));
				}
				
				connection.exec();
				
				return id;
			}
		});
	}

	@Override
	public void updateHpData(HPModel hpModel) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				connection.multi();
				int id = hpModel.getId();
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("hp_num"), ser.serialize(hpModel.getHp_num()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("price"), ser.serialize(String.valueOf(hpModel.getPrice())));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("imageUrl1"), ser.serialize(hpModel.getImageUrl1()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("imageUrl2"), ser.serialize(hpModel.getImageUrl2()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id),
						ser.serialize("imageUrl3"), ser.serialize(hpModel.getImageUrl3()));
				
				connection.exec();
				
				return null;
			}
		});
	}

	@Override
	public void deleteHpData(String id) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				connection.multi();
				connection.del(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + id));
				connection.lRem(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "Id"), 0, ser.serialize(id));
				Set<byte[]> tagKeys = connection.keys(ser.serialize("tag_*"));
				for (byte[] tagKey : tagKeys){
					connection.sRem(tagKey, ser.serialize(id));
				}
				connection.exec();
				
				return null;
			}
		});
	}

	@Override
	public List<HPModel> getHpData(int pageNum, int limit) {
		String id_key = RedisVariableUtil.HP_RECORD_PREFIX + "Id";
		
		return redisTemplate.execute(new RedisCallback<List<HPModel>>() {
			@Override
			public  List<HPModel> doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				connection.multi();
				List<byte[]> id_list = connection.lRange(ser.serialize(id_key), (pageNum - 1) * limit, pageNum * limit);
				
				List<HPModel> hpModelList = new ArrayList<>();
				HPModel hpModel = null;
				for (byte[] id : id_list){
					Map<byte[],byte[]> map = connection.hGetAll(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "." + ser.deserialize(id)));
					hpModel = new HPModel();
					Map<String, String> resultMap = new HashMap<>();
					map.forEach((k, v) -> {
						resultMap.put(ser.deserialize(k), ser.deserialize(v));
					});
					
					hpModel.setHp_num(resultMap.get("hp_num"));
					hpModel.setBrand(resultMap.get("brand"));
					hpModel.setCategory(resultMap.get("category"));
					hpModel.setSize(resultMap.get("size"));
					hpModel.setColor(resultMap.get("color"));
					hpModel.setPrice(Float.parseFloat(resultMap.get("price")));
					hpModel.setImageUrl1(resultMap.get("imageUrl1"));
					hpModel.setImageUrl2(resultMap.get("imageUrl2"));
					hpModel.setImageUrl3(resultMap.get("imageUrl3"));
					hpModelList.add(hpModel);
				}
				
				
				
				connection.exec();
				
				return hpModelList;
			}
		});
		
	}

}
