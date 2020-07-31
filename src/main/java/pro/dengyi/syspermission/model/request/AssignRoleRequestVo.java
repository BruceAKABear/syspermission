package pro.dengyi.syspermission.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 用户角色分配数据传输实体类
 *
 * @author dengy
 */
@Data
@NoArgsConstructor
public class AssignRoleRequestVo {

    @NotBlank
    private String userId;

    @NonNull
    private List<String> roleIds;
}
