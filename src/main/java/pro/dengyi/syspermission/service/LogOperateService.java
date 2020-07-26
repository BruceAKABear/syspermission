package pro.dengyi.syspermission.service;

import pro.dengyi.syspermission.model.LogOperate;

public interface LogOperateService {

    /**
     * 保存操作日志
     *
     * @param logOperate 日志
     */
    void saveLogOperate(LogOperate logOperate);
}
