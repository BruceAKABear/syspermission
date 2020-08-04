package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("log_operate")
public class OperateLog {

    /**
     * 日志ID
     */
    private String id;
    /**
     * 操作模块
     */
    private String opModule;
    /**
     * 操作类型
     */
    private String opType;
    /**
     * 操作描述
     */
    private String opDesc;
    /**
     * 请求参数
     */
    private String opReqParm;
    /**
     * 响应参数
     */
    private String opResParm;
    /**
     * 操作用户ID
     */
    private String opUserId;
    /**
     * 操作用户名
     */
    private String opUserName;
    /**
     * 操作方法
     */
    private String opMethod;
    /**
     * 操作api
     */
    private String opUri;
    /**
     * IP
     */
    private String opIp;
    /**
     * 时间
     */
    private Date opTime;
}
