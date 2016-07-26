package com.cidic.sdx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cidic.sdx.dao.HpIndexDao;
import com.cidic.sdx.model.HPModel;

@Repository
@Component
@Qualifier(value = "hpIndexDaoImpl")
public class HpIndexDaoImpl implements HpIndexDao {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public List<HPModel> getIndexDataByTag(List<String> tagList) {
		
		return null;
	}

}
