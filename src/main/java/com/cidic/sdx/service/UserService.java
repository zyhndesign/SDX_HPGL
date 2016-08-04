package com.cidic.sdx.service;

import java.util.Set;

import com.cidic.sdx.model.UserModel;

public interface UserService {
	
	public UserModel createUser(UserModel user);
    public void updateUser(UserModel user);
    public void deleteUser(Long userId);

    public void correlationRoles(Long userId, Long... roleIds);
    public void uncorrelationRoles(Long userId, Long... roleIds);

    UserModel findOne(Long userId);

    UserModel findByUsername(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
    
    
}
