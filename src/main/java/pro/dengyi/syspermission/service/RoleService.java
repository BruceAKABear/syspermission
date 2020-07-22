package pro.dengyi.syspermission.service;

import pro.dengyi.syspermission.model.request.AssignPermissionRequestVo;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);

    void updateRole(Role role);

    Role findById(String roleId);

    void deleteById(String roleId);

    List<Role> queryAll();

    void assignPerms(AssignPermissionRequestVo vo);

    List<Permission> queryRolePerms(String roleId);
}
