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
		
		return redisTemplate.execute(new RedisCallback<List<HPModel>>() {
			
			@Override
			public  List<HPModel> doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				List<byte[]> id_list = null;//connection.lRange(ser.serialize(id_key), (pageNum - 1) * limit, pageNum * limit);
				
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
				
				
				
				return hpModelList;
			}
		});
	}

}
