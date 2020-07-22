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
public class SystemUser {
    /**
     * ID
     */
    private String id;
    /**
     * 用户名称
     */
    private String phoneNumber;
    /**
     * 密码
     */
    private String password;


}
