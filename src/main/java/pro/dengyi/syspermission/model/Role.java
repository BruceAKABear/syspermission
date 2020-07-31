package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("角色实体")
@TableName("t_role")
public class Role {

    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    private String id;
    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank
    private String name;
    /**
     * 说明
     */
    @ApiModelProperty(value = "角色描述", required = true)
    @NotBlank
    private String description;

}