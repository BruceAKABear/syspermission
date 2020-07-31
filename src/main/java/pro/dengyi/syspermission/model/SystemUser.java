package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户实体类
 *
 * @author dengy
 */
@Data
@TableName("t_user")
@ApiModel("系统用户")
public class SystemUser {
    /**
     * ID
     */
    @ApiModelProperty(hidden = true)
    private String id;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户手机号", required = true)
    @NotBlank
    private String phoneNumber;
    /**
     * 密码
     */
    @JsonIgnore
    @ApiModelProperty(value = "用户密码", required = true)
    @NotBlank
    private String password;
    /**
     * 是否是sass管理员，只有系统初始化添加用户的时候才会添加管理员
     */
    @ApiModelProperty(hidden = true)
    private Boolean isSassAdmin = false;

    @ApiModelProperty(value = "是否是企业管理员", required = true)
    private Boolean isCoAdmin;

    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickName;


}
