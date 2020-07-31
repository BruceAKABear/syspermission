package pro.dengyi.syspermission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;


    @ApiOperation("新增用户")
    @PostMapping("/addUser")
    public BaseResponse addUser(@RequestBody SystemUser systemUser) {
        systemUserService.addUser(systemUser);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public DataResponse<String> login(@RequestBody LoginVo vo) {
        String token = systemUserService.login(vo);
        return new DataResponse(BaseResponseEnum.SUCCESS, token);
    }

    /**
     * 查询当前用户的信息包含权限信息
     *
     * @return
     */
    @ApiOperation("查询用户信息")
    @GetMapping("/userInfo")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<UserInfoDto> userInfo() {
        UserInfoDto userInfoDto = systemUserService.userInfo();
        return new DataResponse(BaseResponseEnum.SUCCESS, userInfoDto);
    }


    @ApiOperation("获取菜单")
    @GetMapping("/getMenus")
    public DataResponse<List<MenuDto>> getMenus() {
        List<MenuDto> menuDtos = systemUserService.getMenus();
        return new DataResponse(BaseResponseEnum.SUCCESS, menuDtos);
    }

    @ApiOperation("用户分页查询")
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
    @ApiOperation("分配角色")
    @PostMapping("/assignRoles")
    public BaseResponse assignRoles(@RequestBody AssignRoleRequestVo vo) {
        systemUserService.assignRoles(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    /**
     * 根据用户ID查询用户已经分配的角色
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    @ApiOperation("查询用户所有角色")
    @GetMapping("/queryUserRoles/{userId}")
    public SingleResponse<List<Role>> queryUserRoles(@PathVariable String userId) {
        List<Role> list = systemUserService.queryUserRoles(userId);
        return new SingleResponse(true, "操作成功", 200, list);
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/userInfo/{userId}")
    public SingleResponse<UserInfoDto> userInfo(@PathVariable String userId) {
        UserInfoDto userInfoDto = systemUserService.userInfo(userId);
        return new SingleResponse(true, "操作成功", 200, userInfoDto);
    }


}
