package com.cidic.sdx.service;

import com.cidic.sdx.model.HPListModel;
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.util.UploadVo;

public interface HpManageService {

	public HPListModel getHpData(int iDisplayStart,int iDisplayLength);
	
	public void insertHpData(HPModel hpModel);
	
	public void updateHpData(HPModel hpModel);
	
	public void deleteHpData(String id);
	
	public HPModel getHpDataById(int id);
	
	public boolean uploadForm(UploadVo uploadVo) throws Exception;
	
	public HPModel getHpDataByHpNum(String hp_num);
}
