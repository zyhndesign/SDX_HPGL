package com.cidic.sdx.service;

import java.util.List;
import java.util.Map;

import com.cidic.sdx.model.BrandModel;

public interface BrandService {
	
	public List<BrandModel> getBrandData(String key);
	
	public Long insertBrandData(String key, String value);
	
	public void updateBrandData(String parentKey,String key, String value);
	
	public void deleteBrandData(String parentKey,String key);
}
