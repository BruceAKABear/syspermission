package pro.dengyi.syspermission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.dengyi.syspermission.common.res.BaseResponse;
import pro.dengyi.syspermission.common.res.BaseResponseEnum;
import pro.dengyi.syspermission.common.res.DataResponse;
import pro.dengyi.syspermission.model.Role;
import pro.dengyi.syspermission.model.SingleResponse;
import pro.dengyi.syspermission.model.SystemUser;
import pro.dengyi.syspermission.model.request.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.request.LoginVo;
import pro.dengyi.syspermission.model.response.MenuDto;
import pro.dengyi.syspermission.model.response.UserInfoDto;
import pro.dengyi.syspermission.service.SystemUserService;

import java.util.List;

/**
 * 用户controller
 *
 * @author dengy
 */
@RestController
@RequestMapping("/user")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;


    @PostMapping("/login")
    public DataResponse<String> login(@RequestBody LoginVo vo) {
        String token = systemUserService.login(vo);
        return new DataResponse(BaseResponseEnum.SUCCESS, token);
    }

    @GetMapping("/getMenus")
    public DataResponse<List<MenuDto>> getMenus() {
        List<MenuDto> menuDtos = systemUserService.getMenus();
        return new DataResponse(BaseResponseEnum.SUCCESS, menuDtos);
    }

    @GetMapping("/userPage/{pageNumber}/{pageSize}")
    public DataResponse<IPage<SystemUser>> userPage(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        IPage<SystemUser> pages = systemUserService.userPage(pageNumber, pageSize);
        return new DataResponse(BaseResponseEnum.SUCCESS, pages);
    }


    /**
     * 新增和修改都使用这一个方法
     *
     * @param vo 数据传输实体
     * @return
     */
    @PostMapping("/assignRoles")
    public BaseResponse assignRoles(AssignRoleRequestVo vo) {
        systemUserService.assignRoles(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    /**
     * 根据用户ID查询用户已经分配的角色
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    @GetMapping("/queryUserRoles/{userId}")
    public SingleResponse<List<Role>> queryUserRoles(@PathVariable String userId) {
        List<Role> list = systemUserService.queryUserRoles(userId);
        return new SingleResponse(true, "操作成功", 200, list);
    }

    @GetMapping("/userInfo")
    public SingleResponse<UserInfoDto> userInfo() {
        UserInfoDto userInfoDto = systemUserService.userInfo();
        return new SingleResponse(true, "操作成功", 200, userInfoDto);
    }


}
