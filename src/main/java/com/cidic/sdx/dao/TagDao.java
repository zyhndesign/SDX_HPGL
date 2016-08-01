package com.cidic.sdx.dao;

import java.util.List;
import java.util.Map;

public interface TagDao {
	
	public void insertTag(String key, String value);
	
	public void updateTag(String key, String old_value, String new_value);
	
	public List<Map<String,String>> getAllTag();
}
