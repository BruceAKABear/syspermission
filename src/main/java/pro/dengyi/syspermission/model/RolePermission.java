package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_role_permission")
@Data
public class RolePermission {
    private String id;
    private String roleId;
    private String permissionId;
}
