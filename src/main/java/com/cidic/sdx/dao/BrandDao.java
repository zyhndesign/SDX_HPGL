package com.cidic.sdx.dao;

import java.util.Map;


public interface BrandDao {

	public Map<String,String> getBrandDataByKey(String key);
	
	public long insertBrandData(String key, String value);
	
	public void updateBrandData(String parentKey,String key, String value);
	
	public void deleteBrandData(String parentKey,String key);
}
