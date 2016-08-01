package com.cidic.sdx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.TagDao;
import com.cidic.sdx.service.TagService;
import com.cidic.sdx.util.RedisVariableUtil;

@Service
@Component
@Qualifier(value = "tagServiceImpl")
public class TagServiceImpl implements TagService {

	private String brand_tag_prefix = RedisVariableUtil.BRAND_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR;
	private String category_tag_prefix = RedisVariableUtil.CATEGORY_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR;
	private String color_tag_prefix = RedisVariableUtil.COLOR_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR;
	private String size_tag_prefix = RedisVariableUtil.SIZE_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR;
			
	@Autowired
	@Qualifier(value = "tagDaoImpl")
	private TagDao tagDaoImpl;
	
	@Override
	public void insertBrandTag(String key, String value) {
		String brandKey = brand_tag_prefix + key;
		tagDaoImpl.insertTag(brandKey, value);
	}

	@Override
	public void updateBrandTag(String key, String oldValue, String newValue) {
		String brandKey = brand_tag_prefix + key;
		tagDaoImpl.updateTag(brandKey, oldValue, newValue);
	}

	@Override
	public void insertCategoryTag(String key, String value) {
		String categoryKey = category_tag_prefix + key;
		tagDaoImpl.insertTag(categoryKey, value);
	}

	@Override
	public void updateCategoryTag(String key, String oldValue, String newValue) {
		String categoryKey = category_tag_prefix + key;
		tagDaoImpl.updateTag(categoryKey, oldValue, newValue);
	}

	@Override
	public void insertColorTag(String key, String value) {
		String colorKey = color_tag_prefix + key;
		tagDaoImpl.insertTag(colorKey, value);
	}

	@Override
	public void updateColorTag(String key, String oldValue, String newValue) {
		String colorKey = color_tag_prefix + key;
		tagDaoImpl.updateTag(colorKey, oldValue, newValue);
	}

	@Override
	public void insertSizeTag(String key, String value) {
		String sizeKey = size_tag_prefix + key;
		tagDaoImpl.insertTag(sizeKey, value);
	}

	@Override
	public void updateSizeTag(String key, String oldValue, String newValue) {
		String sizeKey = size_tag_prefix + key;
		tagDaoImpl.updateTag(sizeKey, oldValue, newValue);
	}

	@Override
	public List<Map<String, String>> getAllTag() {
		// TODO Auto-generated method stub
		return tagDaoImpl.getAllTag();
	}

}
