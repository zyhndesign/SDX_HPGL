package com.cidic.sdx.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.BrandDao;
import com.cidic.sdx.model.BrandModel;
import com.cidic.sdx.service.BrandService;
import com.cidic.sdx.util.RedisVariableUtil;

@Service
@Component
@Qualifier(value = "brandServiceImpl")
public class BrandServiceImpl implements BrandService {

	private String brand_key = RedisVariableUtil.BRAND_PREFIX+RedisVariableUtil.DIVISION_CHAR;
	
	@Autowired
	@Qualifier(value = "brandDaoImpl")
	private BrandDao brandDaoImpl;
	
	
	@Override
	public List<BrandModel> getBrandData(String key) {
		String brandKey = brand_key + key;
		Map<String,String> map = brandDaoImpl.getBrandDataByKey(brandKey);
		List<BrandModel> list = new ArrayList<BrandModel>();
		map.forEach((k,v)->{
			BrandModel boardModel = new BrandModel();
			boardModel.setId(Integer.parseInt(k.split("\\"+RedisVariableUtil.DIVISION_CHAR)[1]));
			boardModel.setName(v);
			boardModel.setpId(Integer.parseInt(key));
			list.add(boardModel);
			Collections.reverse(list);
			
		});
		return list;
	}
	
	@Override
	public Long insertBrandData(String key, String value) {
		
		return brandDaoImpl.insertBrandData(key, value);
	}
	@Override
	public void updateBrandData(String parentKey, String key, String value) {
		brandDaoImpl.updateBrandData(parentKey, key, value);
	}
	@Override
	public void deleteBrandData(String parentKey, String key) {
		brandDaoImpl.deleteBrandData(parentKey, key);
	}
	
	
}
