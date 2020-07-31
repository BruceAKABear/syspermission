package pro.dengyi.syspermission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.dengyi.syspermission.common.res.BaseResponse;
import pro.dengyi.syspermission.common.res.BaseResponseEnum;
import pro.dengyi.syspermission.common.res.DataResponse;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.SingleResponse;
import pro.dengyi.syspermission.model.request.PermissionPageQueryVo;
import pro.dengyi.syspermission.model.request.PermissionRequestVo;
import pro.dengyi.syspermission.service.PermissionService;

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

    @ApiOperation("新增权限")
    @PostMapping("/addPermission")
    public BaseResponse addPermission(@RequestBody PermissionRequestVo vo) {
        permissionService.addPermission(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }
    @ApiOperation("更新权限")
    @PutMapping("/updatePermission")
    public BaseResponse updateRole(PermissionRequestVo vo) {
        permissionService.updatePermission(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }
    @ApiOperation("根据ID查询权限")
    @GetMapping("/findById/{permissionId}")
    public DataResponse<PermissionRequestVo> findById(@PathVariable String permissionId) {
        PermissionRequestVo role = permissionService.findById(permissionId);
        return new DataResponse(BaseResponseEnum.SUCCESS, role);
    }

    @ApiOperation("删除权限")
    @DeleteMapping("/deleteById/{permissionId}")
    public BaseResponse deleteById(@PathVariable String permissionId) {
        permissionService.deleteById(permissionId);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @ApiOperation("权限分页查询")
    @GetMapping("/pageQuery")
    public SingleResponse<IPage<Permission>> pageQuery(PermissionPageQueryVo vo) {
        IPage<Permission> pages = permissionService.pageQuery(vo);
        return new SingleResponse(true, "操作成功", 200, pages);
    }


}
