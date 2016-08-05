package com.cidic.sdx.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cidic.sdx.model.PermissionsModel;
import com.cidic.sdx.model.RoleModel;
import com.cidic.sdx.model.UserModel;
import com.cidic.sdx.service.PermissionsService;
import com.cidic.sdx.service.RoleService;
import com.cidic.sdx.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class UserTest {

	@Autowired
	@Qualifier(value = "userServiceImpl")
	private UserService userServiceImpl;
	
	@Autowired
	@Qualifier(value = "roleServiceImpl")
	private RoleService roleServiceImpl;
	
	@Autowired
	@Qualifier(value = "permissionsServiceImpl")
	private PermissionsService permissionsServiceImpl;
	
	//@Test
	public void createUser(){
		UserModel userModel = new UserModel();
		userModel.setUsername("cidic");
		userModel.setPassword("helloworld");
		List<Integer> roleList = new ArrayList<Integer>();
		roleList.add(1);
		roleList.add(2);
		userModel.setRoleList(roleList);
		
		userServiceImpl.createUser(userModel);
	}
	
	//@Test
	public void updateUser(){
		UserModel userModel = new UserModel();
		userModel.setId(1);
		
	}
	
	//@Test
	public void deleteUser(){
		userServiceImpl.deleteUser(1L);
	}
	
	//@Test
	public void findRoles(){
		Set<String> roles = userServiceImpl.findRoles("cidic4");
		roles.stream().forEach(System.out::println);
	}
	
	@Test 
	public void findPermissions(){
		Set<String> permissions = userServiceImpl.findPermissions("cidic4");
		permissions.stream().forEach(System.out::println);
	}
	
	//@Test 
	public void createRole(){
		RoleModel roleModel1 = new RoleModel();
		roleModel1.setRole("admin");
		roleModel1.setDescription("管理员");
		roleServiceImpl.createRole(roleModel1);
		
		RoleModel roleModel2 = new RoleModel();
		roleModel2.setRole("manage");
		roleModel2.setDescription("经理");
		roleServiceImpl.createRole(roleModel2);
		
		RoleModel roleModel3 = new RoleModel();
		roleModel3.setRole("admin");
		roleModel3.setDescription("部门经理");
		roleServiceImpl.createRole(roleModel3);
		
		RoleModel roleModel4 = new RoleModel();
		roleModel4.setRole("employer");
		roleModel4.setDescription("员工");
		roleServiceImpl.createRole(roleModel4);
	}
	
	//@Test
	public void roleCorrelationPermissions(){
		roleServiceImpl.correlationPermissions(1L, 1L, 2L, 5L);
		
		roleServiceImpl.correlationPermissions(2L, 1L, 3L, 5L);
		
		roleServiceImpl.correlationPermissions(3L, 3L, 2L, 5L);
		
		roleServiceImpl.correlationPermissions(4L, 3L, 2L, 5L, 1L);
	}
	
	//@Test
	public void roleUncorrelationPermissions(){
		roleServiceImpl.uncorrelationPermissions(2L, 5L);
	}
	
	//@Test 
	public void createPermission(){
		
		PermissionsModel permissionsModel1 = new PermissionsModel();
		permissionsModel1.setAvailable(true);
		permissionsModel1.setDescription("更新数据权限");
		permissionsModel1.setPermission("update");
		permissionsServiceImpl.createPermission(permissionsModel1);
		
		PermissionsModel permissionsModel2 = new PermissionsModel();
		permissionsModel2.setAvailable(true);
		permissionsModel2.setDescription("删除数据权限");
		permissionsModel2.setPermission("delete");
		permissionsServiceImpl.createPermission(permissionsModel2);
		
		PermissionsModel permissionsModel3 = new PermissionsModel();
		permissionsModel3.setAvailable(true);
		permissionsModel3.setDescription("查找数据权限");
		permissionsModel3.setPermission("find");
		permissionsServiceImpl.createPermission(permissionsModel1);
		
		PermissionsModel permissionsModel4 = new PermissionsModel();
		permissionsModel4.setAvailable(true);
		permissionsModel4.setDescription("创建数据权限");
		permissionsModel4.setPermission("create");
		permissionsServiceImpl.createPermission(permissionsModel4);
		
	}
	
	//@Test
	public void deletePermission(){
		permissionsServiceImpl.deletePermission(4l);
	}
}
