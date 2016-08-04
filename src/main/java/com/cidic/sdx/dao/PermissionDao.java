package com.cidic.sdx.dao;

import com.cidic.sdx.model.PermissionsModel;

public interface PermissionDao {

	 public PermissionsModel createPermission(PermissionsModel permission);

	 public void deletePermission(Long permissionId);
	    
}
