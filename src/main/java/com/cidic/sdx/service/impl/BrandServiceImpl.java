package com.cidic.sdx.service.impl;

import java.util.ArrayList;
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
@Qualifier(value = "boardServiceImpl")
public class BrandServiceImpl implements BrandService {

	private String brand_key = RedisVariableUtil.BOARD_PREFIX+".";
	
	@Autowired
	@Qualifier(value = "brandDaoImpl")
	private BrandDao brandDaoImpl;
	
	@Override
	public List<BrandModel> getBoardDate(String key) {
		String boardKey = brand_key + key;
		Map<String,String> map = brandDaoImpl.getBrandDateByKey(boardKey);
		
		List<BrandModel> list = new ArrayList<BrandModel>();
		map.forEach((k,v)->{
			BrandModel boardModel = new BrandModel();
			boardModel.setId(Integer.parseInt(k.split(".")[1]));
			boardModel.setBroadName(v);
			list.add(boardModel);
		});
		return list;
	}
	
	@Override
	public Long insertBoardData(String key, String value) {
		
		return brandDaoImpl.insertBrandData(key, value);
	}
	@Override
	public void updateBoardData(String parentKey, String key, String value) {
		brandDaoImpl.updateBrandData(parentKey, key, value);
	}
	@Override
	public void deleteBoardData(String parentKey, String key) {
		brandDaoImpl.deleteBrandData(parentKey, key);
	}
	
	
}
