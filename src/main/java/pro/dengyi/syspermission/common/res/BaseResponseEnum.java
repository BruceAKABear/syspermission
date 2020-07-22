package pro.dengyi.syspermission.common.res;

public enum BaseResponseEnum implements Response {
    SUCCESS(true, 10000, "操作成功"),
    FAIL(false, 11111, "操作失败"),
    USER_EXIST(false, 10001, "用户不存在"),
    PHONE_PASSWORD_ERROR(false, 10001, "用户不存在"),
    ;
    private Boolean status;
    private Integer code;
    private String message;

    BaseResponseEnum(Boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

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
}
