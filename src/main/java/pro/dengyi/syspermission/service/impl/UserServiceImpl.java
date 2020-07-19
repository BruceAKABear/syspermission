package pro.dengyi.syspermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.dengyi.syspermission.dao.RoleDao;
import pro.dengyi.syspermission.dao.UserRoleDao;
import pro.dengyi.syspermission.model.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.UserRole;
import pro.dengyi.syspermission.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleDao roleDao;


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
}
