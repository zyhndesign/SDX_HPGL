package com.cidic.sdx.service;

import com.cidic.sdx.model.PermissionsModel;

public interface PermissionsService {

	public PermissionsModel createPermission(PermissionsModel permission);

	public void deletePermission(Long permissionId);
}
