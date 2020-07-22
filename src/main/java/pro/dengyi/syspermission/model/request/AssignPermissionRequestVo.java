package pro.dengyi.syspermission.model.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignPermissionRequestVo {

    private String roleId;

    private List<String> permIds;
}
