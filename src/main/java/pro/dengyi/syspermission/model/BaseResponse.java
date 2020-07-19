package pro.dengyi.syspermission.model;

import lombok.Data;

@Data
public class BaseResponse {
    private Boolean status;
    private String message;
    private Integer code;

    public BaseResponse(Boolean status, String message, Integer code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
