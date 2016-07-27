package com.cidic.sdx.dao;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class RedisInterTest {

	@Autowired
	@Qualifier(value="redisTemplate")
	RedisTemplate redisTemplate;
	
	@Test
	public void testInterTemplate(){
		String[] keys = {"tag_brand_2","tag_color_2","tag_size_2","tag_size_5","tag_category_1"};
		Set result = redisTemplate.opsForSet().intersect("tag_brand_1", Arrays.asList(keys));
		for (Object data : result){
			System.out.println(data);
		}
	}
}
