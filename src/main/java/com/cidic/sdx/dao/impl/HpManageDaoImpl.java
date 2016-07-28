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
				connection.lPush(ser.serialize("HpIDList"), ser.serialize(String.valueOf(id)));
				//将记录Id存入哈希表
				byte[] hKey = ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id);
				
				connection.hSet(hKey,ser.serialize("hp_num"), ser.serialize(hpModel.getHp_num()));
				connection.hSet(hKey,ser.serialize("brand"), ser.serialize(hpModel.getBrand()));
				connection.hSet(hKey,ser.serialize("category"), ser.serialize(hpModel.getCategory()));
				connection.hSet(hKey,ser.serialize("size"), ser.serialize(hpModel.getSize()));
				connection.hSet(hKey,ser.serialize("color"), ser.serialize(hpModel.getColor()));
				connection.hSet(hKey,ser.serialize("price"), ser.serialize(String.valueOf(hpModel.getPrice())));
				
				if (hpModel.getImageUrl1() != null && !hpModel.getImageUrl1().equals("")){
					connection.hSet(hKey,ser.serialize("imageUrl1"), ser.serialize(hpModel.getImageUrl1()));
				}
				
				if (hpModel.getImageUrl2() != null  && !hpModel.getImageUrl2().equals("")){
					connection.hSet(hKey,ser.serialize("imageUrl2"), ser.serialize(hpModel.getImageUrl2()));
				}

				if (hpModel.getImageUrl3() != null  && !hpModel.getImageUrl3().equals("")){
					connection.hSet(hKey,ser.serialize("imageUrl3"), ser.serialize(hpModel.getImageUrl3()));
				}
				
				
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
				
				System.out.println("执行命令数量："+ resultList.size());
				resultList.stream().forEach((o)->{
					System.out.println("命令执行结果："+o.toString());
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
				logger.info("执行命令数量：", resultList.size());
				resultList.stream().forEach((o)->{
					logger.info("命令执行结果：",o.toString());
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
				logger.info("执行命令数量：", resultList.size());
				resultList.stream().forEach((o)->{
					logger.info("命令执行结果：",o.toString());
				});
				return null;
			}
		});
	}

	@Override
	public List<HPModel> getHpData(int pageNum, int limit) {
		String id_key = "HpIDList";
		
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
					
					
					StringBuilder brandList = new StringBuilder();
					StringBuilder categoryList  = new StringBuilder();
					StringBuilder sizeList = new StringBuilder();
					StringBuilder colorList = new StringBuilder();
					
					Map<byte[],byte[]> mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
					String[] brandArray = resultMap.get("brand").split("\\,");
					for (String brand : brandArray){
						brandList.append(ser.deserialize(mapList.get(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +brand)));
						brandList.append("/");
						mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + brand));
					}
					
					String[] categoryArray = resultMap.get("category").split("\\,");
					for (String category : categoryArray){
						categoryList.append(ser.deserialize(mapList.get(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +category)));
						categoryList.append("/");
						mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + category));
					}
					
					String[] sizeArray = resultMap.get("size").split("\\,");
					for (String size : sizeArray){
						sizeList.append(ser.deserialize(mapList.get(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +size)));
						sizeList.append("/");
						mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + size));
					}
					
					String[] colorArray = resultMap.get("color").split("\\,");
					for (String color : colorArray){
						colorList.append(ser.deserialize(mapList.get(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +color)));
						colorList.append("/");
						mapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + color));
					}
					
					hpModel.setBrandList(brandList.toString());
					hpModel.setCategoryList(categoryList.toString());
					hpModel.setColorList(colorList.toString());
					hpModel.setSizeList(sizeList.toString());
					
					hpModelList.add(hpModel);
				}
				
				
				List<Object> resultList = connection.closePipeline();
				logger.info("执行命令数量：", resultList.size());
				resultList.stream().forEach((o)->{
					logger.info("命令执行结果：",o.toString());
				});
				return hpModelList;
			}
		});
		
	}

	@Override
	public HPModel getHpDataById(int id) {
		
		return redisTemplate.execute(new RedisCallback<HPModel>() {
			@Override
			public  HPModel doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				Map<byte[],byte[]> map = connection.hGetAll(ser.serialize(RedisVariableUtil.HP_RECORD_PREFIX + RedisVariableUtil.DIVISION_CHAR + id));
				
				Map<byte[],byte[]> brandMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> categoryMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.CATEGORY_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> colorMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.COLOR_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> sizeMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.SIZE_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				
				HPModel hpModel = null;
				
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
				
				
				StringBuilder brandList = new StringBuilder();
				StringBuilder categoryList  = new StringBuilder();
				StringBuilder sizeList = new StringBuilder();
				StringBuilder colorList = new StringBuilder();
				
				String[] brandArray = resultMap.get("brand").split("\\,");
				int brandCount = 0;
				for (String brand : brandArray){
					
					String tempKey = RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR +brand;
					brandList.append(ser.deserialize(brandMapList.get(ser.serialize(tempKey))));
					++brandCount;
					if (brandCount != brandArray.length){
						brandList.append("/");
					}
					
					brandMapList = connection.hGetAll(ser.serialize(tempKey));
				}
				
				int categoryCount = 0;
				String[] categoryArray = resultMap.get("category").split("\\,");
				for (String category : categoryArray){
					String tempKey = RedisVariableUtil.CATEGORY_PREFIX + RedisVariableUtil.DIVISION_CHAR +category;
					categoryList.append(ser.deserialize(categoryMapList.get(ser.serialize(tempKey))));
					++categoryCount;
					if (categoryCount != categoryArray.length){
						categoryList.append("/");
					}
					
					categoryMapList = connection.hGetAll(ser.serialize(tempKey));
				}
				
				int sizeCount = 0;
				String[] sizeArray = resultMap.get("size").split("\\,");
				for (String size : sizeArray){
					String tempKey = RedisVariableUtil.SIZE_PREFIX + RedisVariableUtil.DIVISION_CHAR +size;
					sizeList.append(ser.deserialize(sizeMapList.get(ser.serialize(tempKey))));
					++sizeCount;
					if (sizeCount != sizeArray.length){
						sizeList.append("/");
					}
					
					sizeMapList = connection.hGetAll(ser.serialize(tempKey));
				}
				
				int colorCount = 0;
				String[] colorArray = resultMap.get("color").split("\\,");
				for (String color : colorArray){
					String tempKey = RedisVariableUtil.COLOR_PREFIX + RedisVariableUtil.DIVISION_CHAR +color;
					colorList.append(ser.deserialize(colorMapList.get(ser.serialize(tempKey))));
					++colorCount;
					if (colorCount != colorArray.length){
						colorList.append("/");
					}
					
					colorMapList = connection.hGetAll(ser.serialize(tempKey));
				}
				
				hpModel.setBrandList(brandList.toString());
				hpModel.setCategoryList(categoryList.toString());
				hpModel.setColorList(colorList.toString());
				hpModel.setSizeList(sizeList.toString());
					
				
				return hpModel;
			}
		});
	}

}
