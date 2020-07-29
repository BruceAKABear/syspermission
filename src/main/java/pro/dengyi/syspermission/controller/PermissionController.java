package pro.dengyi.syspermission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.dengyi.syspermission.common.res.BaseResponse;
import pro.dengyi.syspermission.common.res.BaseResponseEnum;
import pro.dengyi.syspermission.model.Permission;
import pro.dengyi.syspermission.model.SingleResponse;
import pro.dengyi.syspermission.model.request.PermissionPageQueryVo;
import pro.dengyi.syspermission.model.request.PermissionRequestVo;
import pro.dengyi.syspermission.service.PermissionService;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;


    @PostMapping("/addPermission")
    public BaseResponse addPermission(PermissionRequestVo vo) {
        permissionService.addPermission(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @PutMapping("/updatePermission")
    public BaseResponse updateRole(PermissionRequestVo vo) {
        permissionService.updatePermission(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @GetMapping("/findById/{permissionId}")
    public SingleResponse<PermissionRequestVo> findById(@PathVariable String permissionId) {
        PermissionRequestVo role = permissionService.findById(permissionId);
        return new SingleResponse(true, "操作成功", 200, role);
    }

    @DeleteMapping("/deleteById/{permissionId}")
    public BaseResponse deleteById(@PathVariable String permissionId) {
        permissionService.deleteById(permissionId);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @GetMapping("/pageQuery")
    public SingleResponse<IPage<Permission>> pageQuery(PermissionPageQueryVo vo) {
        IPage<Permission> pages = permissionService.pageQuery(vo);
        return new SingleResponse(true, "操作成功", 200, pages);
    }


}
