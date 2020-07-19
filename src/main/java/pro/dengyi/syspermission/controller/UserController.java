package pro.dengyi.syspermission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.dengyi.syspermission.model.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.BaseResponse;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.SingleResponse;
import pro.dengyi.syspermission.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 新增和修改都使用这一个方法
     *
     * @param vo
     * @return
     */
    @PostMapping("/assignRoles")
    public BaseResponse assignRoles(AssignRoleRequestVo vo) {
        userService.assignRoles(vo);
        return new BaseResponse(true, "操作成功", 200);
    }

    @GetMapping("/queryUserRoles/{userId}")
    public SingleResponse<List<Role>> queryUserRoles(@PathVariable String userId) {
        List<Role> list = userService.queryUserRoles(userId);
        return new SingleResponse(true, "操作成功", 200, list);
    }


}
