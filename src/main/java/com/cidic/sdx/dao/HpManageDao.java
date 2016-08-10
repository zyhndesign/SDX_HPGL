package com.cidic.sdx.dao;

import java.util.Map;

import com.cidic.sdx.model.HPListModel;
import com.cidic.sdx.model.HPModel;

public interface HpManageDao {
	public HPListModel getHpData(int iDisplayStart,int iDisplayLength);
	
	public void insertHpData(HPModel hpModel);
	
	public void updateHpData(HPModel hpModel);
	
	public void deleteHpData(String id);
	
	public HPModel getHpDataById(int id);
	
	public HPModel getHpDataByHpNum(String hp_num);
	
	public Map<String,String> initExcelBaseData();
}
