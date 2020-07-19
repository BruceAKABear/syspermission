package pro.dengyi.syspermission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pro.dengyi.syspermission.model.Permission;

import java.util.List;

@Repository
public interface PermissionDao extends BaseMapper<Permission> {


    List<Permission> queryRolePerms(String roleId);
}
