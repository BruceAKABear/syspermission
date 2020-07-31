package pro.dengyi.syspermission.common.response;

/**
 * 基础响应基类
 *
 * @author dengy
 */
public interface Response {
    /**
     * 获取响应状态
     *
     * @return
     */
    Boolean getStatus();

    /**
     * 获取响应码
     *
     * @return
     */
    Integer getCode();

    /**
     * 获取响应信息
     *
     * @return
     */
    String getMessage();

}
