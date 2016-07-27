package com.cidic.sdx.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.service.HpManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class HpManageTest {

	@Autowired
	@Qualifier(value="redisTemplate")
	RedisTemplate redisTemplate;
	
	@Autowired
	@Qualifier(value = "hpManageServiceImpl")
	private HpManageService hpManageServiceImpl;
	
	//@Test
	public void testInsertHp(){
		HPModel hpModel = new HPModel();
		hpModel.setHp_num("000012");
		hpModel.setBrand("1,2");
		hpModel.setColor("2");
		hpModel.setSize("1");
		hpModel.setCategory("4,5");
		hpModel.setPrice(99.99f);
		hpModel.setImageUrl1("http://oaycvzlnh.bkt.clouddn.com/2939181802662.jpg");
		hpModel.setImageUrl2("http://oaycvzlnh.bkt.clouddn.com/2939181802662.jpg");
		hpModel.setImageUrl3("");
		hpManageServiceImpl.insertHpData(hpModel);
	}
	
	@Test
	public void testHpDataById(){
		HPModel hPModel = hpManageServiceImpl.getHpDataById(7);
		System.out.println(hPModel.getHp_num());
		hPModel.getColorList().stream().forEach(System.out::println);
	}
}
