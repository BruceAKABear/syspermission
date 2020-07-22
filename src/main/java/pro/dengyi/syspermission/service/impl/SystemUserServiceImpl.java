package pro.dengyi.syspermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.dengyi.syspermission.common.exception.BusinessException;
import pro.dengyi.syspermission.common.res.BaseResponseEnum;
import pro.dengyi.syspermission.dao.RoleDao;
import pro.dengyi.syspermission.dao.SystemUserDao;
import pro.dengyi.syspermission.dao.UserRoleDao;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.SystemUser;
import pro.dengyi.syspermission.model.UserRole;
import pro.dengyi.syspermission.model.request.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.request.LoginVo;
import pro.dengyi.syspermission.service.SystemUserService;
import pro.dengyi.syspermission.utils.JwtTokenUtil;
import pro.dengyi.syspermission.utils.PasswordUtil;

import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private SystemUserDao systemUserDao;


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
}
