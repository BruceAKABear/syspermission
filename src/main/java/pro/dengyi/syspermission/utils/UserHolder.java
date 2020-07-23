package pro.dengyi.syspermission.utils;

import org.springframework.stereotype.Component;
import pro.dengyi.syspermission.model.SystemUser;

@Component
public class UserHolder {

    private static ThreadLocal<SystemUser> local = new ThreadLocal<>();

    /**
     * 获取当前登录用户id
     *
     * @return 用户id
     */
    public static String getId() {
        return local.get().getId();
    }

    /**
     * 设置值
     *
     * @param systemUser 用户信息
     */
    public static  void setUser(SystemUser systemUser) {
        local.set(systemUser);
    }

    /**
     * 清空
     */
    public static void clear() {
        local.remove();

    }
}
