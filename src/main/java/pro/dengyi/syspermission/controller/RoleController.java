package pro.dengyi.syspermission.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.dengyi.syspermission.common.response.BaseResponse;
import pro.dengyi.syspermission.common.response.BaseResponseEnum;
import pro.dengyi.syspermission.common.response.DataResponse;
import pro.dengyi.syspermission.model.Role;
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
    @PostMapping("/addUpdateRole")
    @ApiOperation("新增/修改角色")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public BaseResponse addUpdateRole(@RequestBody Role role) {
        roleService.addUpdateRole(role);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @GetMapping("/queryAll")
    @ApiOperation("查询所有角色集合")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<List<Role>> queryAll() {
        List<Role> roles = roleService.queryAll();
        return new DataResponse(BaseResponseEnum.SUCCESS, roles);
    }

    @GetMapping("/queryAssigned/{userId}")
    @ApiOperation("根据用户ID查询所有用户角色集合")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<List<String>> queryAssigned(@PathVariable String userId) {
        List<String> roles = roleService.queryUserAssignedRoles(userId);
        return new DataResponse(BaseResponseEnum.SUCCESS, roles);
    }


    @DeleteMapping("/deleteById/{roleId}")
    @ApiOperation("删除角色")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public BaseResponse deleteById(@PathVariable String roleId) {
        roleService.deleteById(roleId);
        return new BaseResponse(BaseResponseEnum.SUCCESS);

    }

    @PostMapping("/assignPerms")
    @ApiOperation("新增修改角色权限分配")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public BaseResponse assignPerms(@RequestBody AssignPermissionRequestVo vo) {
        roleService.assignPerms(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }


}
