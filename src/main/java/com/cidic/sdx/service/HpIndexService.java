package com.cidic.sdx.service;

import java.util.List;

import com.cidic.sdx.model.HPModel;

public interface HpIndexService {

	public List<HPModel> getIndexDataByTag(List<String> tagList);
	
}
