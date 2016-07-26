package com.cidic.sdx.dao;

import java.util.List;

import com.cidic.sdx.model.HPModel;

public interface HpIndexDao {

	public List<HPModel> getIndexDataByTag(List<String> tagList,int pageNum, int limit);
	
}
