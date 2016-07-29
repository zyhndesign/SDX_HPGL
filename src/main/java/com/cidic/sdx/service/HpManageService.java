package com.cidic.sdx.service;

import java.util.List;

import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.util.UploadVo;

public interface HpManageService {

	public List<HPModel> getHpData(int pageNum, int limit);
	
	public void insertHpData(HPModel hpModel);
	
	public void updateHpData(HPModel hpModel);
	
	public void deleteHpData(String id);
	
	public HPModel getHpDataById(int id);
	
	public boolean uploadForm(UploadVo uploadVo) throws Exception;
}
