package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("pe_permission_button")
@Data
public class PermissionButton {


    /**
     * 主键
     */
    private String id;

    /**
     * 权限代码
     */
    private String buttonClass;

    /**
     * 按钮图标
     */
    private String buttonIcon;

    private String buttonStatus;

}