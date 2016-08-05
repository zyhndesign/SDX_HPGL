package com.cidic.sdx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cidic.sdx.dao.PermissionDao;
import com.cidic.sdx.model.PermissionsModel;
import com.cidic.sdx.service.PermissionsService;

@Service
@Component
@Qualifier(value = "permissionsServiceImpl")
public class PermissionsServiceImpl implements PermissionsService {

	@Autowired
	@Qualifier(value = "permissionsDaoImpl")
	private PermissionDao permissionsDaoImpl;
	
	@Override
	public PermissionsModel createPermission(PermissionsModel permission) {
		
		return permissionsDaoImpl.createPermission(permission);
	}

	@Override
	public void deletePermission(Long permissionId) {
		permissionsDaoImpl.deletePermission(permissionId);
	}

}
