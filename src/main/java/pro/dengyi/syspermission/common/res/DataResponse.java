package pro.dengyi.syspermission.common.res;

import lombok.Data;

/**
 * 基础相应类
 */
@Data
public class DataResponse<T> implements Response {
    private Boolean status;
    private Integer code;
    private String message;
    private T data;

    @Override
    public Boolean getStatus() {
        return status;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 枚举构造
     *
     * @param responseEnum 响应枚举
     */
    public DataResponse(BaseResponseEnum responseEnum, T data) {
        this.status = responseEnum.getStatus();
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    /**
     * 分离参数构造
     *
     * @param status  状态
     * @param code    状态码
     * @param message 消息
     */
    public DataResponse(Boolean status, Integer code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
