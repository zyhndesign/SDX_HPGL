package com.cidic.sdx.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cidic.sdx.model.ColorModel;
import com.cidic.sdx.service.ColorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class ColorTest {

	@Autowired
	@Qualifier(value="colorServiceImpl")
	private ColorService colorServiceImpl;
	
	@Test
	public void getData(){
		List<ColorModel> list = colorServiceImpl.getColorData("0");
		System.out.println(list.size());
	}
}
