package com.cidic.sdx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.HpIndexDao;
import com.cidic.sdx.model.HPListModel;
import com.cidic.sdx.service.HpIndexService;

@Service
@Component
@Qualifier(value = "hpIndexServiceImpl")
public class HpIndexServiceImpl implements HpIndexService {

	@Autowired
	@Qualifier(value = "hpIndexDaoImpl")
	private HpIndexDao hpIndexDaoImpl;
	
	@Override
	public HPListModel getIndexDataByTag(List<String> tagList,int iDisplayStart,int iDisplayLength) {
		return hpIndexDaoImpl.getIndexDataByTag(tagList,iDisplayStart,iDisplayLength);
	}

}
