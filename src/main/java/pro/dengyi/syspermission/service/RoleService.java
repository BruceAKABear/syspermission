package pro.dengyi.syspermission.service;

import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.request.AssignPermissionRequestVo;

import java.util.List;

public interface RoleService {

    /**
     * 新增修改角色，通过ID判断
     *
     * @param role
     */
    void addUpdateRole(Role role);

    void deleteById(String roleId);

    List<Role> queryAll();

    void assignPerms(AssignPermissionRequestVo vo);

    /**
     * 查询用户已经分配的角色列表
     *
     * @param userId
     * @return
     */
    List<String> queryUserAssignedRoles(String userId);


}
