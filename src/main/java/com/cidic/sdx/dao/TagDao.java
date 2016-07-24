package com.cidic.sdx.dao;

public interface TagDao {
	
	public void insertTag(String key, String value);
	
	public void updateTag(String key, String old_value, String new_value);
}
