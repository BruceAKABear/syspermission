package pro.dengyi.syspermission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.dengyi.syspermission.model.*;
import pro.dengyi.syspermission.service.PermissionService;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;


    @PostMapping("/addPermission")
    public BaseResponse addPermission(PermissionRequestVo vo) {
        permissionService.addPermission(vo);
        return new BaseResponse(true, "操作成功", 200);
    }

    @PutMapping("/updatePermission")
    public BaseResponse updateRole(PermissionRequestVo vo) {
        permissionService.updatePermission(vo);
        return new BaseResponse(true, "操作成功", 200);
    }

    @GetMapping("/findById/{permissionId}")
    public SingleResponse<PermissionRequestVo> findById(@PathVariable String permissionId) {
        PermissionRequestVo role = permissionService.findById(permissionId);
        return new SingleResponse(true, "操作成功", 200, role);
    }

    @DeleteMapping("/deleteById/{permissionId}")
    public BaseResponse deleteById(@PathVariable String permissionId) {
        permissionService.deleteById(permissionId);
        return new BaseResponse(true, "操作成功", 200);
    }

    @PostMapping("/pageQuery")
    public SingleResponse<IPage<Permission>> pageQuery(PermissionPageQueryVo vo) {
        IPage<Permission> pages = permissionService.pageQuery(vo);
        return new SingleResponse(true, "操作成功", 200, pages);
    }


}
