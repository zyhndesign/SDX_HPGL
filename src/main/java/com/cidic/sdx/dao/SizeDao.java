package com.cidic.sdx.dao;

import java.util.Map;

public interface SizeDao {
	public Map<String,String> getSizeDataByKey(String key);
	
	public long insertSizeData(String key, String value);
	
	public void updateSizeData(String parentKey,String key, String value);
	
	public void deleteSizeData(String parentKey,String key);
}
