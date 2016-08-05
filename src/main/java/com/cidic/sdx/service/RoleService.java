package com.cidic.sdx.service;

import com.cidic.sdx.model.RoleModel;

public interface RoleService {

	public RoleModel createRole(RoleModel role);
    public void deleteRole(Long roleId);

    public void correlationPermissions(Long roleId, Long... permissionIds);
    public void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
