package pro.dengyi.syspermission.aops;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pro.dengyi.syspermission.model.LogOperate;
import pro.dengyi.syspermission.service.LogOperateService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志切面
 */
@Component
@Aspect
public class LogAspect {
    @Autowired
    private LogOperateService logOperateService;


    /**
     * 操作日志切面
     */
    @Pointcut("execution(* pro.dengyi.syspermission.controller.*.*(..))")
    public void logPointcut() {
    }

    /**
     * 通知
     *
     * @param joinPoint 连接点
     * @param params    响应参数
     */
    @AfterReturning(value = "logPointcut()", returning = "params")
    public void opLog(JoinPoint joinPoint, Object params) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        LogOperate operlog = new LogOperate();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            // OperLog opLog = method.getAnnotation(OperLog.class);
//            if (opLog != null) {
////                String operModul = opLog.operModul();
////                String operType = opLog.operType();
////                String operDesc = opLog.operDesc();
////                operlog.setOpModule(operModul); // 操作模块
////                operlog.setOpType(operType); // 操作类型
////                operlog.setOpDesc(operDesc); // 操作描述
//            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            operlog.setOpMethod(methodName); // 请求方法

            // 请求的参数
            //Map<String, String> rtnMap = converMap(request.getParameterMap());
            // 将参数所在的数组转换成json

            operlog.setOpReqParm(null); // 请求参数
            operlog.setOpResParm(null); // 返回结果
            operlog.setOpUserId(null); // 请求用户ID
            operlog.setOpUserName(null); // 请求用户名称
            operlog.setOpIp(null); // 请求IP
            operlog.setOpUri(request.getRequestURI()); // 请求URI
            operlog.setOpTime(new Date()); // 创建时间
            // logOperateService.saveLogOperate(operlog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
