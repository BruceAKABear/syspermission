package pro.dengyi.syspermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pro.dengyi.syspermission.dao.RoleDao;
import pro.dengyi.syspermission.dao.RolePermissionDao;
import pro.dengyi.syspermission.model.AssignPermissionRequestVo;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.RolePermission;
import pro.dengyi.syspermission.service.PermissionService;
import pro.dengyi.syspermission.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionService permissionService;


    @Override
    @Transactional
    public void addRole(Role role) {
        roleDao.insert(role);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        Role roleSaved = roleDao.selectById(role.getId());
        roleSaved.setName(role.getName());
        roleSaved.setDescription(role.getDescription());
        roleDao.updateById(roleSaved);
    }

    @Override
    public Role findById(String roleId) {
        return roleDao.selectById(roleId);
    }

    @Override
    @Transactional
    public void deleteById(String roleId) {
        roleDao.deleteById(roleId);
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
    public List<Permission> queryRolePerms(String roleId) {
        return permissionService.queryRolePerms(roleId);
    }
}
