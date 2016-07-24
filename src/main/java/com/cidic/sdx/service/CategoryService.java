package com.cidic.sdx.service;

import java.util.List;

import com.cidic.sdx.model.CategoryModel;

public interface CategoryService {
	
	public List<CategoryModel> getCategoryData(String key);
	
	public Long insertCategoryData(String key, String value);
	
	public void updateCategoryData(String parentKey,String key, String value);
	
	public void deleteCategoryData(String parentKey,String key);
}
