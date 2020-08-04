package pro.dengyi.syspermission.service;

import pro.dengyi.syspermission.model.OperateLog;

public interface LogOperateService {

    /**
     * 保存操作日志
     *
     * @param operateLog 日志
     */
    void saveLogOperate(OperateLog operateLog);
}
