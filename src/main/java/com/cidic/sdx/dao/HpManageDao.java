package com.cidic.sdx.dao;

import java.util.List;

import com.cidic.sdx.model.HPModel;

public interface HpManageDao {
	public List<HPModel> getHpData(int pageNum, int limit);
	
	public void insertHpData(HPModel hpModel);
	
	public void updateHpData(HPModel hpModel);
	
	public void deleteHpData(String id);
	
	public HPModel getHpDataById(int id);
}
