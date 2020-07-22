package pro.dengyi.syspermission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.request.PermissionPageQueryVo;
import pro.dengyi.syspermission.model.request.PermissionRequestVo;

import java.util.List;

public interface PermissionService {
    /**
     * 新增权限
     *
     * @param vo 权限实体
     */
    void addPermission(PermissionRequestVo vo);

    void updatePermission(PermissionRequestVo vo);

    PermissionRequestVo findById(String permissionId);

    void deleteById(String permissionId);

    IPage<Permission> pageQuery(PermissionPageQueryVo vo);

    List<Permission> findByTypeAndPid(int i, String permId);

    List<Permission> queryRolePerms(String roleId);
}
