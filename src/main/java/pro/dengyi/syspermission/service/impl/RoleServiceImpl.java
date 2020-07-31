package pro.dengyi.syspermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pro.dengyi.syspermission.common.exception.BusinessException;
import pro.dengyi.syspermission.common.response.BaseResponseEnum;
import pro.dengyi.syspermission.dao.RoleDao;
import pro.dengyi.syspermission.dao.RolePermissionDao;
import pro.dengyi.syspermission.dao.UserRoleDao;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.RolePermission;
import pro.dengyi.syspermission.model.UserRole;
import pro.dengyi.syspermission.model.request.AssignPermissionRequestVo;
import pro.dengyi.syspermission.service.PermissionService;
import pro.dengyi.syspermission.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleDao userRoleDao;


    @Override
    @Transactional
    public void addUpdateRole(Role role) {
        //查询角色名是否存在
        QueryWrapper<Role> qr = new QueryWrapper<>();
        qr.eq("name", role.getName());
        Role roleSaved = roleDao.selectOne(qr);
        if (StringUtils.isEmpty(role.getId())) {
            //新增
            if (roleSaved != null) {
                throw new BusinessException(BaseResponseEnum.ROLE_EXIST);
            }
            roleDao.insert(role);
        } else {
            if (!roleSaved.getId().equals(role.getId())) {
                throw new BusinessException(BaseResponseEnum.ROLE_EXIST);
            }
            roleDao.updateById(role);
        }

    }

    /**
     * 角色删除时需要注意的问题
     * 1. 用户绑定角色后角色不能删除
     * 2. 角色分配权限后角色不能删除
     *
     * @param roleId
     */
    @Override
    @Transactional
    public void deleteById(String roleId) {
        QueryWrapper<UserRole> qrr = new QueryWrapper<>();
        qrr.eq("role_id", roleId);
        List<UserRole> userRoles = userRoleDao.selectList(qrr);

        //判断角色有没有被权限绑定
        QueryWrapper<RolePermission> qr = new QueryWrapper<>();
        qr.eq("role_id", roleId);
        List<RolePermission> rolePermissions = rolePermissionDao.selectList(qr);
        //同时为空才能删除
        if (CollectionUtils.isEmpty(rolePermissions) && CollectionUtils.isEmpty(userRoles)) {
            roleDao.deleteById(roleId);
        } else {
            throw new BusinessException(BaseResponseEnum.FAIL);
        }

    }

    @Override
    public List<Role> queryAll() {
        QueryWrapper<Role> qr = new QueryWrapper<>();
        return roleDao.selectList(qr);
    }

    @Override
    @Transactional
    public void assignPerms(AssignPermissionRequestVo vo) {
        //先删除后新增，就可以兼容新增和修改
        QueryWrapper<RolePermission> qr = new QueryWrapper<>();
        qr.eq("role_id", vo.getRoleId());
        rolePermissionDao.delete(qr);
        RolePermission rp = new RolePermission();
        for (String permId : vo.getPermIds()) {
            //1. 先保存按钮或者菜单
            rp.setId(null);
            rp.setRoleId(vo.getRoleId());
            rp.setPermissionId(permId);
            rolePermissionDao.insert(rp);
            //2. 将其对应的api默认保存
            List<Permission> list = permissionService.findByTypeAndPid(3, permId);
            if (!CollectionUtils.isEmpty(list)) {
                for (Permission permission : list) {
                    rp.setId(null);
                    rp.setRoleId(vo.getRoleId());
                    rp.setPermissionId(permission.getId());
                    rolePermissionDao.insert(rp);
                }

            }
        }

    }


    @Override
    public List<String> queryUserAssignedRoles(String userId) {
        List<String> roles = new ArrayList<>();
        QueryWrapper<UserRole> qr = new QueryWrapper<>();
        qr.eq("user_id", userId);
        List<UserRole> userRoles = userRoleDao.selectList(qr);
        if (!CollectionUtils.isEmpty(userRoles)) {
            for (UserRole userRole : userRoles) {
                roles.add(userRole.getRoleId());
            }
        }
        return roles;
    }
}
