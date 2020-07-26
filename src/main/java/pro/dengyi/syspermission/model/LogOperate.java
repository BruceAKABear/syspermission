package pro.dengyi.syspermission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("log_operate")
public class LogOperate {

    private String id;
    private String opModule;
    private String opType;
    private String opDesc;
    private String opReqParm;
    private String opResParm;
    private String opUserId;
    private String opUserName;
    private String opMethod;
    private String opUri;
    private String opIp;
    private Date opTime;
}
