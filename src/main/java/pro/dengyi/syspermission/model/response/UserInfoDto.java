package pro.dengyi.syspermission.model.response;

import lombok.Data;
import pro.dengyi.syspermission.model.SystemUser;

import java.util.List;

@Data
public class UserInfoDto {
    /**
     * 用户信息
     */
    private SystemUser userInfo;
    /**
     * 按钮权限集合
     */
    private List<MenuTree> buttons;
    /**
     * 菜单权限集合
     */
    private List<String> menus;
}
