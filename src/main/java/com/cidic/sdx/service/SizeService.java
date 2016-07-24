package com.cidic.sdx.service;

import java.util.List;

import com.cidic.sdx.model.SizeModel;

public interface SizeService {
	
	public List<SizeModel> getSizeData(String key);
	
	public Long insertSizeData(String key, String value);
	
	public void updateSizeData(String parentKey,String key, String value);
	
	public void deleteSizeData(String parentKey,String key);
}
