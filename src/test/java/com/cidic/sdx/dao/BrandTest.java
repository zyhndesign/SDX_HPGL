package com.cidic.sdx.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cidic.sdx.model.BrandModel;
import com.cidic.sdx.service.BrandService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class BrandTest {

	@Autowired
	@Qualifier(value="brandServiceImpl")
	BrandService brandServiceImpl;
	
	//@Test
	public void insertData(){
		brandServiceImpl.insertBrandData("board", "hello");
	}
	
	@Test
	public void getData(){
		List<BrandModel> list = brandServiceImpl.getBrandData("1");
		System.out.println(list.size());
	}
}
