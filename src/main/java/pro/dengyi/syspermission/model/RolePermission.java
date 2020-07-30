package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色权限中间表
 *
 * @author dengy
 */
@TableName("t_role_permission")
@Data
public class RolePermission {
    private String id;
    private String roleId;
    private String permissionId;
}
