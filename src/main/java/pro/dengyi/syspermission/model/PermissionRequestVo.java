package pro.dengyi.syspermission.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

//这个是类注解,表示该类实例化的对象里，值为null的字段不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PermissionRequestVo {

    //------基本数据-----------
    private String id;
    private String description;
    private String name;
    //1菜单权限2按钮权限3api权限
    private Integer type;
    private String pid;
    private String code;
    //--------扩展数据---------
    //menu 1
    private String menuIcon;
    private String menuOrder;
    //按钮 2
    private String buttonClass;
    private String buttonIcon;
    private String buttonStatus;
    //api 3
    private String apiLevel;
    private String apiMethod;
    private String apiUrl;

}
