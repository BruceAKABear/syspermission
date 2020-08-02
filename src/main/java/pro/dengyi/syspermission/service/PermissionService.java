package pro.dengyi.syspermission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.request.PermissionPageQueryVo;
import pro.dengyi.syspermission.model.request.PermissionRequestVo;
import pro.dengyi.syspermission.model.vo.PermCondition;

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


    /**
     * 查询完整权限树
     *
     * @return
     */
    List<Permission> getPermTree();

    /**
     * 根据条件查询权限树
     *
     * @return
     */
    List<Permission> getPermTree(PermCondition condition);

    /**
     * 根据条件插叙所有按钮code集合
     *
     * @param condition
     * @return
     */
    List<String> getPermButtons(PermCondition condition);

    /**
     * 根据角色ID查询所有已绑定的权限ID集合
     *
     * @param roleId
     * @return
     */
    List<String> getRolePermIds(String roleId);

    /**
     * 更具父ID查询下一级权限列表
     *
     * @param pid 父ID
     * @return
     */
    List<Permission> getChildrenByPid(String pid);
}
