package pro.dengyi.syspermission.common.exception;


import pro.dengyi.syspermission.common.res.BaseResponseEnum;

/**
 * 业务异常类
 *
 * @author dengy
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BusinessException(BaseResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
