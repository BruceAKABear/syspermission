package pro.dengyi.syspermission.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.dengyi.syspermission.common.exception.BusinessException;
import pro.dengyi.syspermission.common.res.BaseResponseEnum;
import pro.dengyi.syspermission.dao.PermissionDao;
import pro.dengyi.syspermission.dao.RoleDao;
import pro.dengyi.syspermission.dao.SystemUserDao;
import pro.dengyi.syspermission.dao.UserRoleDao;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.SystemUser;
import pro.dengyi.syspermission.model.UserRole;
import pro.dengyi.syspermission.model.request.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.request.LoginVo;
import pro.dengyi.syspermission.model.response.MenuDto;
import pro.dengyi.syspermission.model.response.UserInfoDto;
import pro.dengyi.syspermission.service.SystemUserService;
import pro.dengyi.syspermission.utils.JwtTokenUtil;
import pro.dengyi.syspermission.utils.PasswordUtil;
import pro.dengyi.syspermission.utils.UserHolder;

import java.util.ArrayList;
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
    private PermissionDao permissionDao;


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
    public List<Role> queryUserRoles(String userId) {
        return roleDao.queryUserRoles(userId);
    }

    @Override
    public String login(LoginVo vo) {
        QueryWrapper<SystemUser> qr = new QueryWrapper<>();
        qr.eq("phone_number", vo.getPhone());
        SystemUser systemUser = systemUserDao.selectOne(qr);
        if (systemUser == null) {
            throw new BusinessException(BaseResponseEnum.USER_EXIST);
        }
        Boolean match = PasswordUtil.match(vo.getPassword(), systemUser.getPassword());
        if (!match) {
            throw new BusinessException(BaseResponseEnum.PHONE_PASSWORD_ERROR);
        }
        //生成token
        return JwtTokenUtil.genToken(systemUser);
    }

    @Override
    public List<MenuDto> getMenus() {

        List<MenuDto> menus = new ArrayList<>();
        //判断用户类型
        SystemUser systemUser = systemUserDao.selectById(UserHolder.getId());
        if (systemUser.getIsSassAdmin()) {
            //sass管理员查询所有
            //获取第一级菜单
            QueryWrapper<Permission> qr = new QueryWrapper<>();
            qr.eq("type", 1);
            qr.isNull("pid");
            List<Permission> list = permissionDao.selectList(qr);
            for (Permission permission : list) {
                MenuDto menuDto = new MenuDto();
                //封装dto
                BeanUtil.copyProperties(permission, menuDto);

                //查询第二级
                QueryWrapper<Permission> qrr = new QueryWrapper<>();
                qrr.eq("type", 1);
                qrr.eq("pid", permission.getId());
                List<Permission> listSub = permissionDao.selectList(qrr);
                List<MenuDto> subMenus = new ArrayList<>();
                for (Permission permissionSub : listSub) {
                    MenuDto menuSubDto = new MenuDto();
                    BeanUtil.copyProperties(permissionSub, menuSubDto);
                    subMenus.add(menuSubDto);
                }
                menuDto.setChildren(subMenus);
                //添加进列表
                menus.add(menuDto);

            }

        } else if (systemUser.getIsCoAdmin()) {
            //企业管理员查询企业所有权限,同时是可见的才行
            //获取第一级菜单
            QueryWrapper<Permission> qr = new QueryWrapper<>();
            qr.eq("type", 1);
            qr.eq("en_visible", 1);
            qr.isNull("pid");
            List<Permission> list = permissionDao.selectList(qr);
            for (Permission permission : list) {
                MenuDto menuDto = new MenuDto();
                //封装dto
                BeanUtil.copyProperties(permission, menuDto);

                //查询第二级
                QueryWrapper<Permission> qrr = new QueryWrapper<>();
                qrr.eq("type", 1);
                qrr.eq("pid", permission.getId());
                List<Permission> listSub = permissionDao.selectList(qrr);
                List<MenuDto> subMenus = new ArrayList<>();
                for (Permission permissionSub : listSub) {
                    MenuDto menuSubDto = new MenuDto();
                    BeanUtil.copyProperties(permissionSub, menuSubDto);
                    subMenus.add(menuSubDto);
                }
                menuDto.setChildren(subMenus);
                //添加进列表
                menus.add(menuDto);

            }
        } else {
            //普通用户更具角色查
        }

        return menus;
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
        //判断用户类型，如果是超级管理员查询所有权限
        userInfoDto.setButtons(new ArrayList<>());
        userInfoDto.setMenus(new ArrayList<>());

        return userInfoDto;
    }

    @Override
    public void addUser(SystemUser systemUser) {
        systemUserDao.insert(systemUser);
    }
}
