package pro.dengyi.syspermission.model.response;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

    private String id;
    private String name;
    private Integer type;
    private String code;
    private String description;
    private List<MenuDto> children;
}
