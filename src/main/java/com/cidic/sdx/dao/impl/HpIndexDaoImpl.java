package com.cidic.sdx.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cidic.sdx.dao.HpIndexDao;
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "hpIndexDaoImpl")
public class HpIndexDaoImpl implements HpIndexDao {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public List<HPModel> getIndexDataByTag(List<String> tagList,int pageNum, int limit) {
		
		StringBuilder tagListStr = new StringBuilder();
		tagList.stream().forEach((s)->tagListStr.append(s));
		String cacheKey = DigestUtils.md5Hex(tagListStr.toString());
		
		return redisTemplate.execute(new RedisCallback<List<HPModel>>() {
			
			@Override
			public  List<HPModel> doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				if (!connection.exists(ser.serialize(cacheKey))){
					Set<String> result = redisTemplate.opsForSet().intersect(tagList.get(0), tagList);
					result.stream().forEach((s)->{
						connection.lPush(ser.serialize(cacheKey), ser.serialize(s));
					});
					connection.expire(ser.serialize(cacheKey), 300);
				}
				
				List<byte[]> id_list = connection.lRange(ser.serialize(cacheKey), (pageNum - 1) * limit, pageNum * limit);
				

				Map<byte[],byte[]> brandMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.BRAND_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> categoryMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.CATEGORY_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> colorMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.COLOR_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				Map<byte[],byte[]> sizeMapList = connection.hGetAll(ser.serialize(RedisVariableUtil.SIZE_PREFIX + RedisVariableUtil.DIVISION_CHAR + "0"));
				
				//connection.sInter(id_list);
				
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
					
					hpModelList.add(hpModel);
				}
				
			
				return hpModelList;
			}
		});
	}

}
