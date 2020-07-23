package pro.dengyi.syspermission;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pro.dengyi.syspermission.common.exception.BusinessException;
import pro.dengyi.syspermission.common.res.BaseResponseEnum;
import pro.dengyi.syspermission.model.SystemUser;
import pro.dengyi.syspermission.utils.JwtTokenUtil;
import pro.dengyi.syspermission.utils.UserHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author dengy
 */
@Component
public class LoginIntercepter extends HandlerInterceptorAdapter {


    /**
     * 进入控制器之前进行参数的封装
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                throw new BusinessException(BaseResponseEnum.NEED_LOGIN);
            }
            HandlerMethod hm = (HandlerMethod) handler;
            //解析token
            SystemUser systemUser = JwtTokenUtil.decToken(token);
            UserHolder.setUser(systemUser);
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.clear();
    }
}
