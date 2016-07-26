package com.cidic.sdx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.HpManageDao;
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.service.HpManageService;

@Service
@Component
@Qualifier(value = "hpManageServiceImpl")
public class HpManageServiceImpl implements HpManageService {

	
	@Autowired
	@Qualifier(value = "hpManageDaoImpl")
	private HpManageDao hpManageDaoImpl;
	
	@Override
	public List<HPModel> getHpData(int pageNum, int limit) {
		
		return hpManageDaoImpl.getHpData(pageNum, limit);
	}

	@Override
	public void insertHpData(HPModel hpModel) {
		hpManageDaoImpl.insertHpData(hpModel);
	}

	@Override
	public void updateHpData(HPModel hpModel) {
		hpManageDaoImpl.updateHpData(hpModel);
	}

	@Override
	public void deleteHpData(String id) {
		hpManageDaoImpl.deleteHpData(id);
	}

}
