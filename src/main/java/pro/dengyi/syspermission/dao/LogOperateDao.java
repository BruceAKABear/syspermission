package pro.dengyi.syspermission.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pro.dengyi.syspermission.model.OperateLog;

@Repository
public interface LogOperateDao extends BaseMapper<OperateLog> {
}
