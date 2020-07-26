package pro.dengyi.syspermission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.SystemUser;
import pro.dengyi.syspermission.model.request.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.request.LoginVo;
import pro.dengyi.syspermission.model.response.MenuDto;

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

    /**
     * 查询当前用户的菜单
     *
     * @return
     */
    List<MenuDto> getMenus();

    IPage<SystemUser> userPage(Integer pageNumber, Integer pageSize);
}
