package com.cidic.sdx.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.ColorDao;
import com.cidic.sdx.model.ColorModel;
import com.cidic.sdx.service.ColorService;
import com.cidic.sdx.util.RedisVariableUtil;

@Service
@Component
@Qualifier(value = "colorServiceImpl")
public class ColorServiceImpl implements ColorService {

	private String color_key = RedisVariableUtil.COLOR_PREFIX+RedisVariableUtil.DIVISION_CHAR;
	
	@Autowired
	@Qualifier(value = "colorDaoImpl")
	private ColorDao colorDaoImpl;
	
	@Override
	public List<ColorModel> getColorData(String key) {
		String colorKey = color_key + key;
		Map<String,String> map = colorDaoImpl.getColorDataByKey(colorKey);
		List<ColorModel> list = new ArrayList<>();
		map.forEach((k,v)->{
			ColorModel colorModel = new ColorModel();
			colorModel.setId(Integer.parseInt(k.split("\\"+RedisVariableUtil.DIVISION_CHAR)[1]));
			colorModel.setName(v);
			colorModel.setpId(Integer.parseInt(key));
			list.add(colorModel);
			Collections.reverse(list);
			
		});
		return list;
	}

	@Override
	public Long insertColorData(String key, String value) {
		// TODO Auto-generated method stub
		return colorDaoImpl.insertColorData(key, value);
	}

	@Override
	public void updateColorData(String parentKey, String key, String value) {
		colorDaoImpl.updateColorData(parentKey, key, value);
	}

	@Override
	public void deleteColorData(String parentKey, String key) {
		colorDaoImpl.deleteColorData(parentKey, key);

	}

}
