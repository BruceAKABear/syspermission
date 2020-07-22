package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户实体类
 *
 * @author dengy
 */
@TableName("t_user")
@Data
public class User {
    /**
     * ID
     */
    private String id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;


}
