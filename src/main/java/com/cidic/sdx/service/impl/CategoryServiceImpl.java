package com.cidic.sdx.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.CategoryDao;
import com.cidic.sdx.model.CategoryModel;
import com.cidic.sdx.service.CategoryService;
import com.cidic.sdx.util.RedisVariableUtil;

@Service
@Component
@Qualifier(value = "categoryServiceImpl")
public class CategoryServiceImpl implements CategoryService {

	private String category_key = RedisVariableUtil.CATEGORY_PREFIX+RedisVariableUtil.DIVISION_CHAR;
	
	@Autowired
	@Qualifier(value = "categoryDaoImpl")
	private CategoryDao categoryDaoImpl;
	
	@Override
	public List<CategoryModel> getCategoryData(String key) {
		String categoryKey = category_key + key;
		Map<String,String> map = categoryDaoImpl.getCategoryDataByKey(categoryKey);
		List<CategoryModel> list = new ArrayList<>();
		map.forEach((k,v)->{
			CategoryModel categoryModel = new CategoryModel();
			categoryModel.setId(Integer.parseInt(k.split("\\"+RedisVariableUtil.DIVISION_CHAR)[1]));
			categoryModel.setName(v);
			categoryModel.setpId(Integer.parseInt(key));
			list.add(categoryModel);
			Collections.reverse(list);
			
		});
		return list;
	}

	@Override
	public Long insertCategoryData(String key, String value) {
		return categoryDaoImpl.insertCategoryData(key, value);
	}

	@Override
	public void updateCategoryData(String parentKey, String key, String value) {
		categoryDaoImpl.updateCategoryData(parentKey, key, value);

	}

	@Override
	public void deleteCategoryData(String parentKey, String key) {
		categoryDaoImpl.deleteCategoryData(parentKey, key);
	}

}
