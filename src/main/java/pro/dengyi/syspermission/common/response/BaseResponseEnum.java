package pro.dengyi.syspermission.common.response;

public enum BaseResponseEnum implements Response {
    SUCCESS(true, 10000, "操作成功"),
    USER_EXIST(false, 10001, "用户已存在"),
    USER_NOT_EXIST(false, 10002, "用户不存在"),
    ROLE_EXIST(false, 10003, "角色已存在"),
    FAIL(false, 11111, "操作失败"),

    PHONE_PASSWORD_ERROR(false, 10002, "用户不存在"),
    NEED_LOGIN(false, 10003, "当前操作需要登录"),
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
