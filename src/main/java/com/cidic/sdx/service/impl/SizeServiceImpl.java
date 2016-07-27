package com.cidic.sdx.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.SizeDao;
import com.cidic.sdx.model.SizeModel;
import com.cidic.sdx.service.SizeService;
import com.cidic.sdx.util.RedisVariableUtil;

@Service
@Component
@Qualifier(value = "sizeServiceImpl")
public class SizeServiceImpl implements SizeService {

	private String size_key = RedisVariableUtil.SIZE_PREFIX+RedisVariableUtil.DIVISION_CHAR;
	
	@Autowired
	@Qualifier(value = "sizeDaoImpl")
	private SizeDao sizeDaoImpl;
	
	@Override
	public List<SizeModel> getSizeData(String key) {
		String sizeKey = size_key + key;
		Map<String,String> map = sizeDaoImpl.getSizeDataByKey(sizeKey);
		List<SizeModel> list = new ArrayList<>();
		map.forEach((k,v)->{
			SizeModel sizeModel = new SizeModel();
			sizeModel.setId(Integer.parseInt(k.split("\\"+RedisVariableUtil.DIVISION_CHAR)[1]));
			sizeModel.setName(v);
			sizeModel.setpId(Integer.parseInt(key));
			list.add(sizeModel);
			Collections.reverse(list);
			
		});
		return list;
	}

	@Override
	public Long insertSizeData(String key, String value) {
		return sizeDaoImpl.insertSizeData(key, value);
	}

	@Override
	public void updateSizeData(String parentKey, String key, String value) {
		sizeDaoImpl.updateSizeData(parentKey, key, value);
	}

	@Override
	public void deleteSizeData(String parentKey, String key) {
		sizeDaoImpl.deleteSizeData(parentKey, key);
	}

}
