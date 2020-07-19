package pro.dengyi.syspermission.model;

import lombok.Data;

@Data
public class SingleResponse<T> {
    private Boolean status;
    private String message;
    private Integer code;
    private T data;

    public SingleResponse(Boolean status, String message, Integer code, T data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
