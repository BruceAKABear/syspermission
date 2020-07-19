package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_user_role")
@Data
public class UserRole {

    private String id;
    private String userId;
    private String roleId;
}
