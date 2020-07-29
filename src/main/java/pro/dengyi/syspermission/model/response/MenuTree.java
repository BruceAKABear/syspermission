package pro.dengyi.syspermission.model.response;

import lombok.Data;

import java.util.List;

/**
 * 菜单树
 *
 * @author dengy
 */
@Data
public class MenuTree {
    /**
     * 主键ID
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型1菜单2按钮3api
     */
    private Integer type;
    /**
     * 编码
     */
    private String code;
    /**
     * 描述
     */
    private String description;
    /**
     * 下一级
     */
    private List<MenuTree> children;
}
