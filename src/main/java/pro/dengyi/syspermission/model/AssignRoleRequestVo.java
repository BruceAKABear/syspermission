package pro.dengyi.syspermission.model;

import lombok.Data;

import java.util.List;

@Data
public class AssignRoleRequestVo {

    private String userId;

    private List<String> roleIds;
}
