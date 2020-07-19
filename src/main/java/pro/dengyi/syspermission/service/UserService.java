package pro.dengyi.syspermission.service;

import pro.dengyi.syspermission.model.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.Role;

import java.util.List;

public interface UserService {
    void assignRoles(AssignRoleRequestVo vo);

    List<Role> queryUserRoles(String userId);
}
