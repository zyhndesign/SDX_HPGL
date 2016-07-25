package com.cidic.sdx.service;

import java.util.List;

import com.cidic.sdx.model.HPModel;

public interface HpManageService {

	public List<HPModel> getHpData(String id);
	
	public void insertHpData(HPModel hpModel);
	
	public void updateHpData(HPModel hpModel);
	
	public void deleteHpData(String id);
}
