package com.cidic.sdx.dao;

import java.util.Map;

public interface CategoryDao {

	public Map<String,String> getCategoryDataByKey(String key);
	
	public long insertCategoryData(String key, String value);
	
	public void updateCategoryData(String parentKey,String key, String value);
	
	public void deleteCategoryData(String parentKey,String key);
	
}
