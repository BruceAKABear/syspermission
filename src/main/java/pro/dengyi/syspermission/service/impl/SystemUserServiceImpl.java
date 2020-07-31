package pro.dengyi.syspermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.dengyi.syspermission.common.exception.BusinessException;
import pro.dengyi.syspermission.common.response.BaseResponseEnum;
import pro.dengyi.syspermission.dao.RoleDao;
import pro.dengyi.syspermission.dao.SystemUserDao;
import pro.dengyi.syspermission.dao.UserRoleDao;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.SystemUser;
import pro.dengyi.syspermission.model.UserRole;
import pro.dengyi.syspermission.model.request.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.request.LoginVo;
import pro.dengyi.syspermission.model.response.UserInfoDto;
import pro.dengyi.syspermission.model.vo.PermCondition;
import pro.dengyi.syspermission.service.PermissionService;
import pro.dengyi.syspermission.service.SystemUserService;
import pro.dengyi.syspermission.utils.JwtTokenUtil;
import pro.dengyi.syspermission.utils.PasswordUtil;
import pro.dengyi.syspermission.utils.UserHolder;

import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private SystemUserDao systemUserDao;
    @Autowired
    private PermissionService permissionService;


    @Override
    @Transactional
    public void addUser(SystemUser systemUser) {
        //查询手机是否已经注册
        QueryWrapper<SystemUser> qr = new QueryWrapper<>();
        qr.eq("phone_number", systemUser.getPhoneNumber());
        SystemUser systemUserSaved = systemUserDao.selectOne(qr);
        if (systemUserSaved != null) {
            throw new BusinessException(BaseResponseEnum.USER_EXIST);
        }
        systemUserDao.insert(systemUser);
    }

    @Override
    @Transactional
    public void assignRoles(AssignRoleRequestVo vo) {
        //先删除后新增，就可以兼容新增和修改
        QueryWrapper<UserRole> qr = new QueryWrapper<>();
        qr.eq("user_id", vo.getUserId());
        userRoleDao.delete(qr);
        UserRole userRole = new UserRole();
        for (String roleId : vo.getRoleIds()) {
            userRole.setId(null);
            userRole.setUserId(vo.getUserId());
            userRole.setRoleId(roleId);
            userRoleDao.insert(userRole);
        }


    }

    @Override
    public String login(LoginVo vo) {
        //查询用户
        QueryWrapper<SystemUser> qr = new QueryWrapper<>();
        qr.eq("phone_number", vo.getPhone());
        SystemUser systemUser = systemUserDao.selectOne(qr);
        if (systemUser == null) {
            throw new BusinessException(BaseResponseEnum.USER_NOT_EXIST);
        }
        //密码校验
        Boolean match = PasswordUtil.match(vo.getPassword(), systemUser.getPassword());
        if (!match) {
            throw new BusinessException(BaseResponseEnum.PHONE_PASSWORD_ERROR);
        }
        //生成token
        return JwtTokenUtil.genToken(systemUser);
    }

    @Override
    public IPage<SystemUser> userPage(Integer pageNumber, Integer pageSize) {
        QueryWrapper<SystemUser> qr = new QueryWrapper<>();
        IPage<SystemUser> page = new Page<>(pageNumber == null ? 1 : pageNumber, pageSize == null ? 10 : pageSize);
        return systemUserDao.selectPage(page, qr);
    }

    @Override
    public UserInfoDto userInfo() {
        UserInfoDto userInfoDto = new UserInfoDto();
        SystemUser systemUser = systemUserDao.selectById(UserHolder.getId());
        userInfoDto.setUserInfo(systemUser);
        List<Permission> permTree = null;
        //判断用户类型，如果是超级管理员查询所有权限
        if (systemUser.getIsSassAdmin()) {
            //超管拥有全部权限,只为了控制菜单，所以不查全部
            permTree = permissionService.getPermTree(new PermCondition(false, 1));

        } else if (systemUser.getIsCoAdmin()) {
            //企业管理员获取除了超管特有的权限以外所有权限
            permTree = permissionService.getPermTree(new PermCondition(false, 2));
        } else {
            //用户通过角色获取
            permTree = permissionService.getPermTree(new PermCondition(false, 3, systemUser.getId()));
        }
        userInfoDto.setMenuTree(permTree);
        //查询所有按钮权限
        List<String> permButtons = permissionService.getPermButtons(new PermCondition(systemUser.getIsSassAdmin() ? 1 : systemUser.getIsCoAdmin() ? 2 : 3));
        userInfoDto.setButtons(permButtons);
        return userInfoDto;
    }

}
