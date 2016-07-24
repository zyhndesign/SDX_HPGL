package com.cidic.sdx.service;

import java.util.List;

import com.cidic.sdx.model.ColorModel;

public interface ColorService {
	
	public List<ColorModel> getColorData(String key);
	
	public Long insertColorData(String key, String value);
	
	public void updateColorData(String parentKey,String key, String value);
	
	public void deleteColorData(String parentKey,String key);
}
