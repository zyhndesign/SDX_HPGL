package com.cidic.sdx.service.impl;

import java.util.Set;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.UserDao;
import com.cidic.sdx.model.UserModel;
import com.cidic.sdx.service.UserService;

@Service
@Component
@Qualifier(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier(value = "userDaoImpl")
	private UserDao userDaoImpl;
	
	
	@Override
	public UserModel createUser(UserModel user) {

		String password = user.getPassword();
	 
		SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();  
		randomNumberGenerator.setSeed("SDX_HPGL".getBytes());  
		String salt = randomNumberGenerator.nextBytes().toHex(); 
		String md5_password = new Md5Hash(password, salt).toString();
		user.setSlot(salt);
		user.setPassword(md5_password);
		
		return userDaoImpl.createUser(user);
	}

	@Override
	public void updateUser(UserModel user) {
		userDaoImpl.updateUser(user);
	}

	@Override
	public void deleteUser(Long userId) {
		userDaoImpl.deleteUser(userId);
	}

	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		userDaoImpl.correlationRoles(userId, roleIds);
	}

	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		userDaoImpl.uncorrelationRoles(userId, roleIds);
	}

	@Override
	public UserModel findOne(Long userId) {
		return userDaoImpl.findOne(userId);
	}

	@Override
	public UserModel findByUsername(String username) {
		return userDaoImpl.findByUsername(username);
	}

	@Override
	public Set<String> findRoles(String username) {
		
		return userDaoImpl.findRoles(username);
	}

	@Override
	public Set<String> findPermissions(String username) {
		
		return userDaoImpl.findPermissions(username);
	}

}
