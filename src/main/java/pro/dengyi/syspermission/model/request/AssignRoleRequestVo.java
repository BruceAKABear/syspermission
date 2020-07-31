package pro.dengyi.syspermission.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 用户角色分配数据传输实体类
 *
 * @author dengy
 */
@Data
@ApiModel("用户分配角色实体")
public class AssignRoleRequestVo {

    @NotBlank
    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;

    @NonNull
    @ApiModelProperty(value = "角色ID集合", required = true)
    private List<String> roleIds;
}
