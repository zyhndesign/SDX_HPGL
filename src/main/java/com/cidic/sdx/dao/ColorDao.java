package com.cidic.sdx.dao;

import java.util.Map;

public interface ColorDao {
	
	public Map<String,String> getColorDataByKey(String key);
	
	public long insertColorData(String key, String value);
	
	public void updateColorData(String parentKey,String key, String value);
	
	public void deleteColorData(String parentKey,String key);
}
