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
import pro.dengyi.syspermission.model.SystemUser;
import pro.dengyi.syspermission.model.request.AssignRoleRequestVo;
import pro.dengyi.syspermission.model.request.LoginVo;
import pro.dengyi.syspermission.model.response.UserInfoDto;
import pro.dengyi.syspermission.service.SystemUserService;

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
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public BaseResponse addUser(@RequestBody SystemUser systemUser) {
        systemUserService.addUser(systemUser);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }

    @ApiOperation("登录，返回token前端其他接口header必须带token=''")
    @PostMapping("/login")
    public DataResponse<String> login(@RequestBody LoginVo vo) {
        String token = systemUserService.login(vo);
        return new DataResponse(BaseResponseEnum.SUCCESS, token);
    }

    @ApiOperation("查询用户信息,包含用户菜单树，按钮数组")
    @GetMapping("/userInfo")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<UserInfoDto> userInfo() {
        UserInfoDto userInfoDto = systemUserService.userInfo();
        return new DataResponse(BaseResponseEnum.SUCCESS, userInfoDto);
    }


    @ApiOperation("用户分页查询")
    @GetMapping("/userPage/{pageNumber}/{pageSize}")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public DataResponse<IPage<SystemUser>> userPage(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        IPage<SystemUser> pages = systemUserService.userPage(pageNumber, pageSize);
        return new DataResponse(BaseResponseEnum.SUCCESS, pages);
    }

    @ApiOperation("分配角色")
    @PostMapping("/assignRoles")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token", required = true)})
    public BaseResponse assignRoles(@RequestBody AssignRoleRequestVo vo) {
        systemUserService.assignRoles(vo);
        return new BaseResponse(BaseResponseEnum.SUCCESS);
    }


}
