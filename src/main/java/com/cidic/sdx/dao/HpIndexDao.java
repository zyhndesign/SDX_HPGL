package com.cidic.sdx.dao;

import java.util.List;

import com.cidic.sdx.model.HPListModel;

public interface HpIndexDao {

	public HPListModel getIndexDataByTag(List<String> tagList,int iDisplayStart,int iDisplayLength);
	
}
