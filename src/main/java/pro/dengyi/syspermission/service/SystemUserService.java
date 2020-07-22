package pro.dengyi.syspermission.service;

import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.request.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.request.LoginVo;

import java.util.List;

public interface SystemUserService {
    void assignRoles(AssignRoleRequestVo vo);

    List<Role> queryUserRoles(String userId);

    /**
     * 登录方法
     *
     * @param vo
     * @return
     */
    String login(LoginVo vo);
}
