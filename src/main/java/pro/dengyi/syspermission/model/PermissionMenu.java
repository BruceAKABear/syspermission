package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("pe_permission_menu")
@Data
public class PermissionMenu {

    /**
     * 主键
     */
    private String id;

    /**
     * 图标
     */
    private String menuIcon;

    /**
     * 序号
     */
    private String menuOrder;
}