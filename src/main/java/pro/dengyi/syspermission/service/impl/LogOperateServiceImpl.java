package pro.dengyi.syspermission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.dengyi.syspermission.dao.LogOperateDao;
import pro.dengyi.syspermission.model.LogOperate;
import pro.dengyi.syspermission.service.LogOperateService;

@Service
public class LogOperateServiceImpl implements LogOperateService {

    @Autowired
    private LogOperateDao logOperateDao;

    @Override
    @Transactional
    public void saveLogOperate(LogOperate logOperate) {
        logOperateDao.insert(logOperate);

    }
}
