package pro.dengyi.syspermission.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.dengyi.syspermission.common.res.BaseResponse;
import pro.dengyi.syspermission.common.res.BaseResponseEnum;
import pro.dengyi.syspermission.common.res.DataResponse;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.SingleResponse;
import pro.dengyi.syspermission.model.request.AssignPermissionRequestVo;
import pro.dengyi.syspermission.service.RoleService;

import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @PostMapping("/addRole")
    public BaseResponse addRole(@RequestBody Role role) {
        roleService.addRole(role);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @PutMapping("/updateRole")
    public BaseResponse updateRole(Role role) {
        roleService.updateRole(role);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @GetMapping("/findById/{roleId}")
    public SingleResponse<Role> findById(@PathVariable String roleId) {
        Role role = roleService.findById(roleId);
        return new SingleResponse(true, "操作成功", 200, role);
    }

    /**
     * 查询所有角色集合
     *
     * @return
     */
    @GetMapping("/queryAll")
    public SingleResponse<List<Role>> queryAll() {
        List<Role> roles = roleService.queryAll();
        return new SingleResponse(true, "操作成功", 200, roles);
    }

    /**
     * 查询用户已分配的角色
     *
     * @return
     */
    @GetMapping("/queryAssigned/{userId}")
    public DataResponse<List<String>> queryAssigned(@PathVariable String userId) {
        List<String> roles = roleService.queryAssignedRoles(userId);
        return new DataResponse(BaseResponseEnum.SUCCESS, roles);
    }


    @DeleteMapping("/deleteById/{roleId}")
    public BaseResponse deleteById(@PathVariable String roleId) {
        roleService.deleteById(roleId);
        return new BaseResponse(BaseResponseEnum.SUCCESS);

    }

    /**
     * 新增和修改都使用这一个方法
     *
     * @param vo
     * @return
     */
    @PostMapping("/assignPerms")
    public BaseResponse assignPerms(AssignPermissionRequestVo vo) {
        roleService.assignPerms(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    /**
     * 根据角色查询权限用于回显
     *
     * @param roleId
     * @return
     */
    @GetMapping("/queryRolePerms/{roleId}")
    public SingleResponse<List<Permission>> queryRolePerms(@PathVariable String roleId) {
        List<Permission> perms = roleService.queryRolePerms(roleId);
        return new SingleResponse(true, "操作成功", 200, perms);
    }


}
