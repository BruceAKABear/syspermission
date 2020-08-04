package pro.dengyi.syspermission.aops;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pro.dengyi.syspermission.anno.OperLog;
import pro.dengyi.syspermission.model.OperateLog;
import pro.dengyi.syspermission.service.LogOperateService;
import pro.dengyi.syspermission.utils.UserHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志切面
 */
@Component
@Aspect
public class LogAspect {
    @Autowired
    private LogOperateService logOperateService;


    /**
     * 操作日志切点
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
        OperateLog operlog = new OperateLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);
            //只对加了注解的进行日志记录
            if (opLog != null) {
                operlog.setOpModule(opLog.opModule());
                operlog.setOpType(opLog.opType());
                operlog.setOpDesc(opLog.opDesc());
                // 获取请求的类名
                String className = joinPoint.getTarget().getClass().getName();
                // 获取请求的方法名
                String methodName = method.getName();
                methodName = className + "." + methodName;
                operlog.setOpMethod(methodName);
                // 请求的参数
                Map<String, String> rtnMap = converMap(request.getParameterMap());
                ObjectMapper om = new ObjectMapper();
                operlog.setOpReqParm(om.writeValueAsString(rtnMap));
                operlog.setOpResParm(om.writeValueAsString(params));
                operlog.setOpUserId(UserHolder.getId());
                operlog.setOpUserName(UserHolder.getUser().getNickName());
                //TODO
                operlog.setOpIp("");
                operlog.setOpUri(request.getRequestURI());
                operlog.setOpTime(new Date());
                logOperateService.saveLogOperate(operlog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }
}
