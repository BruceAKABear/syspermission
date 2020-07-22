package pro.dengyi.syspermission.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author dengy
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Map<String, Object> customException(Exception e) {
        log.error("框架异常，信息为{}", e.getMessage());
        Map<String, Object> returnmap = new HashMap<>(3);
        //执行状态
        returnmap.put("status", false);
        returnmap.put("code", 999999);
        returnmap.put("message", "服务器开小差了");
        return returnmap;
    }

    /**
     * 业务异常处理类
     *
     * @param be 业务异常类
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Map<String, Object> businessException(BusinessException be) {
        log.error("业务异常，信息为{}", be.getMessage());
        Map<String, Object> returnmap = new HashMap<>(3);
        //执行状态
        returnmap.put("status", false);
        returnmap.put("code", be.getCode());
        returnmap.put("message", be.getMessage());

        return returnmap;
    }

//    /**
//     * 参数异常处理类
//     *
//     * @param me 参数异常类
//     * @return
//     */
//    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
//    public Map<String, Object> parametersException(Exception me) {
//        log.error("请求参数异常，信息为{}", me.getMessage());
//        Map<String, Object> returnmap = new HashMap<>(3);
//        //执行状态
//        returnmap.put("status", false);
//        returnmap.put("code", BusinessExceptionCodeEnum.PARAM_ERROR.getCode());
//        returnmap.put("message", BusinessExceptionCodeEnum.PARAM_ERROR.getMessage());
//
//        return returnmap;
//    }

//    /**
//     * 参数异常处理类
//     *
//     * @param ee 参数异常类
//     * @return
//     */
//    @ExceptionHandler(ExpiredJwtException.class)
//    public Map<String, Object> jwtExpireException(ExpiredJwtException ee) {
//        log.error("jwt过期异常，信息为{}", ee.getMessage());
//        Map<String, Object> returnmap = new HashMap<>(3);
//        //执行状态
//        returnmap.put("status", false);
//        returnmap.put("code", BusinessExceptionCodeEnum.TOKEN_EXPIRE.getCode());
//        returnmap.put("message", BusinessExceptionCodeEnum.TOKEN_EXPIRE.getMessage());
//
//        return returnmap;
//    }


}
