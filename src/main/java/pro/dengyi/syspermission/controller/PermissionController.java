package pro.dengyi.syspermission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.dengyi.syspermission.common.response.BaseResponse;
import pro.dengyi.syspermission.common.response.BaseResponseEnum;
import pro.dengyi.syspermission.common.response.DataResponse;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.request.PermissionPageQueryVo;
import pro.dengyi.syspermission.model.request.PermissionRequestVo;
import pro.dengyi.syspermission.service.PermissionService;

import java.util.List;

/**
 * 权限controller
 *
 * @author dengy
 */

@Api(tags = "权限接口")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @PostMapping("/addPermission")
    @ApiOperation("新增权限")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public BaseResponse addPermission(@RequestBody PermissionRequestVo vo) {
        permissionService.addPermission(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }


    @PutMapping("/updatePerm")
    @ApiOperation("更新权限")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public BaseResponse updateRole(PermissionRequestVo vo) {
        permissionService.updatePermission(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }


    @GetMapping("/getChildrenListByPid/{pid}")
    @ApiOperation("根据父ID查询下一级权限列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<List<Permission>> getChildrenByPid(@PathVariable String pid) {
        List<Permission> childrenList = permissionService.getChildrenByPid(pid);
        return new DataResponse(BaseResponseEnum.SUCCESS, childrenList);
    }

    @GetMapping("/findById/{permissionId}")
    @ApiOperation("根据ID查询权限")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<PermissionRequestVo> findById(@PathVariable String permissionId) {
        PermissionRequestVo role = permissionService.findById(permissionId);
        return new DataResponse(BaseResponseEnum.SUCCESS, role);
    }


    @DeleteMapping("/deleteById/{permissionId}")
    @ApiOperation("删除权限")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public BaseResponse deleteById(@PathVariable String permissionId) {
        permissionService.deleteById(permissionId);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }


    @GetMapping("/pageQuery")
    @ApiOperation("权限分页查询,只查第一级菜单")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<IPage<Permission>> pageQuery(PermissionPageQueryVo vo) {
        IPage<Permission> pages = permissionService.pageQuery(vo);
        return new DataResponse(BaseResponseEnum.SUCCESS, pages);
    }

    @GetMapping("/getPermTree")
    @ApiOperation("请求完整权限树")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<List<Permission>> getPermTree() {
        List<Permission> permTree = permissionService.getPermTree();
        return new DataResponse(BaseResponseEnum.SUCCESS, permTree);
    }

    @GetMapping("/getRolePermIds/{roleId}")
    @ApiOperation("根据角色ID查询角色绑定的权限ID集合")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<List<String>> getRolePermIds(@PathVariable String roleId) {
        List<String> permIds = permissionService.getRolePermIds(roleId);
        return new DataResponse(BaseResponseEnum.SUCCESS, permIds);
    }


}
