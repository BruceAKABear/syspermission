package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_permission")
@Data
public class Permission  {
    /**
     * 主键
     */
    private String id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    private Integer type;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 父id
     */
    private String pid;

    //可见状态0的话查询系统级别，1是企业级别
    private Boolean enVisible;

}