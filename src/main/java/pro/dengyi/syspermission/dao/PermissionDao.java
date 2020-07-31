package pro.dengyi.syspermission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pro.dengyi.syspermission.model.Permission;

import java.util.List;

@Repository
public interface PermissionDao extends BaseMapper<Permission> {


    List<Permission> queryRolePerms(String roleId);

    /**
     * 根据用户ID查询所有一级权限（菜单权限集合）
     *
     * @param userId
     * @return
     */
    List<Permission> selectFirstByUserId(String userId);

    /**
     * 根据用户ID查询所有按钮权限集合
     *
     * @param userId
     * @return
     */
    List<Permission> selectButtonsByUserId(String userId);
}
