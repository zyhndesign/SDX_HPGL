package com.cidic.sdx.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(HpManageDaoImpl.class);
	
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
				
				connection.openPipeline();
				//将记录Id存入队列
				connection.lPush(bIdKey, ser.serialize(String.valueOf(id)));
				//将记录Id存入哈希表
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("hp_num"), ser.serialize(hpModel.getHp_num()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("brand"), ser.serialize(hpModel.getBrand()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("category"), ser.serialize(hpModel.getCategory()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("size"), ser.serialize(hpModel.getSize()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("color"), ser.serialize(hpModel.getColor()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("price"), ser.serialize(String.valueOf(hpModel.getPrice())));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("imageUrl1"), ser.serialize(hpModel.getImageUrl1()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("imageUrl2"), ser.serialize(hpModel.getImageUrl2()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("imageUrl3"), ser.serialize(hpModel.getImageUrl3()));
				
				String[] brandArray = hpModel.getBrand().split("\\,");
				for (String s : brandArray){
					String key = RedisVariableUtil.BRAND_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR +s;
					connection.sAdd(ser.serialize(key), ser.serialize(String.valueOf(id)));
				}
				
				String[] categoryArray = hpModel.getCategory().split("\\,");
				for (String s : categoryArray){
					String key = RedisVariableUtil.CATEGORY_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR +s;
					connection.sAdd(ser.serialize(key), ser.serialize(String.valueOf(id)));
				}

				String[] sizeArray = hpModel.getSize().split("\\,");
				for (String s : sizeArray){
					String key = RedisVariableUtil.COLOR_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR +s;
					connection.sAdd(ser.serialize(key), ser.serialize(String.valueOf(id)));
				}

				String[] colorArray = hpModel.getColor().split("\\,");
				for (String s : colorArray){
					String key = RedisVariableUtil.SIZE_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR +s;
					connection.sAdd(ser.serialize(key), ser.serialize(String.valueOf(id)));
				}
				
				List<Object> resultList = connection.closePipeline();
				logger.debug("执行命令数量：", resultList.size());
				resultList.stream().forEach((o)->{
					logger.debug("命令执行结果：",o.toString());
				});
				
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
				
				connection.openPipeline();
				int id = hpModel.getId();
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("hp_num"), ser.serialize(hpModel.getHp_num()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("price"), ser.serialize(String.valueOf(hpModel.getPrice())));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("imageUrl1"), ser.serialize(hpModel.getImageUrl1()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("imageUrl2"), ser.serialize(hpModel.getImageUrl2()));
				connection.hSet(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id),
						ser.serialize("imageUrl3"), ser.serialize(hpModel.getImageUrl3()));
				
				List<Object> resultList = connection.closePipeline();
				logger.debug("执行命令数量：", resultList.size());
				resultList.stream().forEach((o)->{
					logger.debug("命令执行结果：",o.toString());
				});
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
				
				connection.openPipeline();
				connection.del(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id));
				connection.lRem(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + "Id"), 0, ser.serialize(id));
				Set<byte[]> tagKeys = connection.keys(ser.serialize("tag_*"));
				for (byte[] tagKey : tagKeys){
					connection.sRem(tagKey, ser.serialize(id));
				}
				List<Object> resultList = connection.closePipeline();
				logger.debug("执行命令数量：", resultList.size());
				resultList.stream().forEach((o)->{
					logger.debug("命令执行结果：",o.toString());
				});
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
				
				
				connection.openPipeline();
				
				List<byte[]> id_list = connection.lRange(ser.serialize(id_key), (pageNum - 1) * limit, pageNum * limit);
				
				List<HPModel> hpModelList = new ArrayList<>();
				HPModel hpModel = null;
				for (byte[] id : id_list){
					Map<byte[],byte[]> map = connection.hGetAll(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + ser.deserialize(id)));
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
					
					
					List<String> brandList = new ArrayList<>();
					List<String> categoryList  = new ArrayList<>();
					List<String> sizeList = new ArrayList<>();
					List<String> colorList = new ArrayList<>();
					
					Map<byte[],byte[]> mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
					String[] brandArray = resultMap.get("brand").split("\\,");
					for (String brand : brandArray){
						brandList.add(ser.deserialize(mapList.get(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +brand)));
						mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + brand));
					}
					
					String[] categoryArray = resultMap.get("category").split("\\,");
					for (String category : categoryArray){
						categoryList.add(ser.deserialize(mapList.get(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +category)));
						mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + category));
					}
					
					String[] sizeArray = resultMap.get("size").split("\\,");
					for (String size : sizeArray){
						sizeList.add(ser.deserialize(mapList.get(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +size)));
						mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + size));
					}
					
					String[] colorArray = resultMap.get("color").split("\\,");
					for (String color : colorArray){
						colorList.add(ser.deserialize(mapList.get(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +color)));
						mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + color));
					}
					
					hpModel.setBrandList(brandList);
					hpModel.setCategoryList(categoryList);
					hpModel.setColorList(colorList);
					hpModel.setSizeList(sizeList);
					
					hpModelList.add(hpModel);
				}
				
				
				List<Object> resultList = connection.closePipeline();
				logger.debug("执行命令数量：", resultList.size());
				resultList.stream().forEach((o)->{
					logger.debug("命令执行结果：",o.toString());
				});
				return hpModelList;
			}
		});
		
	}

}
