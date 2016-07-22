package com.cidic.sdx.service;

import java.util.List;
import java.util.Map;

import com.cidic.sdx.model.BrandModel;

public interface BrandService {
	
	public List<BrandModel> getBoardData(String key);
	
	public Long insertBoardData(String key, String value);
	
	public void updateBoardData(String parentKey,String key, String value);
	
	public void deleteBoardData(String parentKey,String key);
}
