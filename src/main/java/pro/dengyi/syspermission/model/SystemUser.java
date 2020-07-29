package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;
    /**
     * 是否是sass管理员
     */
    private Boolean isSassAdmin;
    private Boolean isCoAdmin;
    private String nickName;


}
