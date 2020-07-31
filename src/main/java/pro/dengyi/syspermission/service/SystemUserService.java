package pro.dengyi.syspermission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import pro.dengyi.syspermission.model.SystemUser;
import pro.dengyi.syspermission.model.request.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.request.LoginVo;
import pro.dengyi.syspermission.model.response.UserInfoDto;

/**
 * 系统用户service接口
 */
public interface SystemUserService {

    /**
     * 新增用户
     *
     * @param systemUser 用户信息实体
     */
    void addUser(SystemUser systemUser);

    /**
     * 分配角色
     *
     * @param vo
     */
    void assignRoles(AssignRoleRequestVo vo);

    /**
     * 登录方法
     *
     * @param vo
     * @return
     */
    String login(LoginVo vo);

    /**
     * 用户分页查询
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    IPage<SystemUser> userPage(Integer pageNumber, Integer pageSize);


    /**
     * 查询当前登录用户的用户信息
     *
     * @return
     */
    UserInfoDto userInfo();

}
