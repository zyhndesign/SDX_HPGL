package com.cidic.sdx.service;

import java.util.List;
import java.util.Map;

public interface TagService {

	public void insertBrandTag(String key,String value);
	
	public void updateBrandTag(String key, String oldValue, String newValue);
	
	public void insertCategoryTag(String key,String value);
	
	public void updateCategoryTag(String key, String oldValue, String newValue);
	
	public void insertColorTag(String key,String value);
	
	public void updateColorTag(String key, String oldValue, String newValue);
	
	public void insertSizeTag(String key,String value);
	
	public void updateSizeTag(String key, String oldValue, String newValue);
	
	public List<Map<String, String>> getAllTag();
}
