package pro.dengyi.syspermission;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
            HandlerMethod hm = (HandlerMethod) handler;
            RequestMapping annotation = hm.getClass().getAnnotation(RequestMapping.class);
            System.err.println(annotation);
            RequestMapping methodAnnotation = hm.getMethodAnnotation(RequestMapping.class);
            System.err.println(methodAnnotation);
            System.err.println(request.getRequestURL().toString());
//            System.err.println(request.);
        }
        return true;
    }

}
