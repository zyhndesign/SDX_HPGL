package com.cidic.sdx.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class RedisTest {

	@Autowired
	@Qualifier(value="redisTemplate")
	RedisTemplate redisTemplate;
	
	@Test
	public void testStringTemplate(){
		
	    /*
	    ValueOperations<String, List<String>> value = redisTemplate.opsForValue();
	    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
	    ListOperations<String, List<String>> list = redisTemplate.opsForList();
	    HyperLogLogOperations<String, List<String>> hyperLogLog = redisTemplate.opsForHyperLogLog();
	    SetOperations<String, List<String>> set = redisTemplate.opsForSet();
	    ZSetOperations<String, List<String>> zSet = redisTemplate.opsForZSet();
	    List<String> listValue = new ArrayList<String>();
	    listValue.add("001");
	    listValue.add("002");
	    value.set("list", listValue);
	    System.out.println(value.get("list"));
	    */
		
	    ValueOperations<String, User> valueOper = redisTemplate.opsForValue();  
        User u1 = new User("zhangsan",12);  
        User u2 = new User("lisi",25);  
        valueOper.set("u:u1", u1);  
        valueOper.set("u:u2", u2);  
        System.out.println(valueOper.get("u:u1").getName());  
        System.out.println(valueOper.get("u:u2").getName());  
	    
	}
}
