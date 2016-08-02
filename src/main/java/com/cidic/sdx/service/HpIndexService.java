package com.cidic.sdx.service;

import java.util.List;

import com.cidic.sdx.model.HPListModel;

public interface HpIndexService {

	public HPListModel getIndexDataByTag(List<String> tagList,int iDisplayStart,int iDisplayLength);
	
}
