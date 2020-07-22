package pro.dengyi.syspermission.common.res;

import lombok.Data;

/**
 * 基础相应类
 */
@Data
public class BaseResponse implements Response {
    private Boolean status;
    private Integer code;
    private String message;

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
    public BaseResponse(BaseResponseEnum responseEnum) {
        this.status = responseEnum.getStatus();
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    /**
     * 分离参数构造
     *
     * @param status  状态
     * @param code    状态码
     * @param message 消息
     */
    public BaseResponse(Boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
