package pro.dengyi.syspermission.model.response;

import lombok.Data;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.SystemUser;

import java.util.List;

/**
 * 用户信息数据传输实体类
 *
 * @author dengy
 */
@Data
public class UserInfoDto {
    /**
     * 用户信息
     */
    private SystemUser userInfo;
    /**
     * 菜单树
     */
    private List<Permission> menuTree;
    /**
     * 按钮集合
     */
    private List<String> buttons;
}
