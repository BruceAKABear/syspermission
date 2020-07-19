package pro.dengyi.syspermission.model;

import lombok.Data;

@Data
public class PermissionPageQueryVo {

    /**
     * 父id
     */
    private String pid;
    /**
     * 是否普通企业可见
     */
    private Boolean enVisible;
    /**
     * 查询类型0菜单按钮，1菜单2按钮3api
     */
    private Integer type;

    /**
     * 页码
     */
    private Integer pageNumber;
    /**
     * 页面大小
     */
    private Integer pageSize;

}
