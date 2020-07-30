package pro.dengyi.syspermission.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//这个是类注解,表示该类实例化的对象里，值为null的字段不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PermissionRequestVo {

    //------基本数据-----------
    private String id;
    @NotBlank
    private String description;
    @NotBlank
    private String name;
    //1菜单权限2按钮权限3api权限
    @NotNull
    private Integer type;
    private String pid;
    @NotBlank
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
