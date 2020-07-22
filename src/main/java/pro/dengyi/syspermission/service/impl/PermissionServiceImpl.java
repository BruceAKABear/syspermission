package pro.dengyi.syspermission.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pro.dengyi.syspermission.dao.PermissionApiDao;
import pro.dengyi.syspermission.dao.PermissionButtonDao;
import pro.dengyi.syspermission.dao.PermissionDao;
import pro.dengyi.syspermission.dao.PermissionMenuDao;
import pro.dengyi.syspermission.model.*;
import pro.dengyi.syspermission.model.request.PermissionPageQueryVo;
import pro.dengyi.syspermission.model.request.PermissionRequestVo;
import pro.dengyi.syspermission.service.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;


    @Autowired
    private PermissionButtonDao permissionButtonDao;

    @Autowired
    private PermissionApiDao permissionApiDao;


    @Override
    @Transactional
    public void addPermission(PermissionRequestVo vo) {

        //基础数据封装
        Permission permission = new Permission();
        BeanUtil.copyProperties(vo, permission);
        permissionDao.insert(permission);
        Integer type = permission.getType();
        switch (type) {
            case 1:
                //菜单权限
                //封装扩展数据
                PermissionMenu pm = new PermissionMenu();
                BeanUtil.copyProperties(vo, pm);
                pm.setId(permission.getId());
                permissionMenuDao.insert(pm);
                break;
            case 2:
                //按钮权限
                PermissionButton pb = new PermissionButton();
                BeanUtil.copyProperties(vo, pb);
                pb.setId(permission.getId());
                permissionButtonDao.insert(pb);
                break;
            case 3:
                //api权限
                PermissionApi pa = new PermissionApi();
                BeanUtil.copyProperties(vo, pa);
                pa.setId(permission.getId());
                permissionApiDao.insert(pa);
                break;
            default:
                System.out.println("参数错误");
        }

    }

    @Override
    @Transactional
    public void updatePermission(PermissionRequestVo vo) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(vo, permission);
        permissionDao.updateById(permission);
        switch (vo.getType()) {
            case 1:
                //菜单权限
                //封装扩展数据
                PermissionMenu pm = new PermissionMenu();
                BeanUtil.copyProperties(vo, pm);
                permissionMenuDao.updateById(pm);
                break;
            case 2:
                //按钮权限
                PermissionButton pb = new PermissionButton();
                BeanUtil.copyProperties(vo, pb);
                permissionButtonDao.updateById(pb);
                break;
            case 3:
                //api权限
                PermissionApi pa = new PermissionApi();
                BeanUtil.copyProperties(vo, pa);
                permissionApiDao.updateById(pa);
                break;
            default:
                System.out.println("参数错误");
        }
    }

    @Override
    public PermissionRequestVo findById(String permissionId) {
        Permission permission = permissionDao.selectById(permissionId);
        PermissionRequestVo res = new PermissionRequestVo();
        BeanUtil.copyProperties(permission, res);
        switch (permission.getType()) {
            case 1:
                PermissionMenu permissionMenu = permissionMenuDao.selectById(permission.getId());
                BeanUtil.copyProperties(permissionMenu, res);
                break;
            case 2:
                PermissionButton permissionButton = permissionButtonDao.selectById(permission.getId());
                BeanUtil.copyProperties(permissionButton, res);
                break;
            case 3:
                PermissionApi permissionApi = permissionApiDao.selectById(permission.getId());
                BeanUtil.copyProperties(permissionApi, res);
                break;
            default:
                System.out.println("参数错误");
        }

        return res;
    }

    @Override
    @Transactional
    public void deleteById(String permissionId) {
        Permission permission = permissionDao.selectById(permissionId);
        permissionDao.deleteById(permission.getId());
        switch (permission.getType()) {
            case 1:
                permissionMenuDao.deleteById(permission.getId());
                break;
            case 2:
                permissionButtonDao.deleteById(permission.getId());
                break;
            case 3:
                permissionApiDao.deleteById(permission.getId());
                break;
            default:
                System.out.println("参数错误");
        }
    }

    @Override
    public IPage<Permission> pageQuery(PermissionPageQueryVo vo) {
        Integer pageNumber = vo.getPageNumber();
        Integer pageSize = vo.getPageSize();
        IPage<Permission> page = new Page<>(pageNumber == null ? 1 : pageNumber, pageSize == null ? 10 : pageSize);
        QueryWrapper<Permission> qr = new QueryWrapper<>();
        //条件判断
        if (!StringUtils.isEmpty(vo.getPid())) {
            qr.eq("pid", vo.getPid());
        }
        if (vo.getEnVisible() != null) {
            qr.eq("en_visible", vo.getEnVisible());
        }
        if (vo.getType() != null) {
            switch (vo.getType()) {
                case 0:
                    qr.in("type", 1, 2);
                    break;
                case 1:
                    qr.eq("type", 1);
                    break;
                case 2:
                    qr.eq("type", 2);
                    break;
                case 3:
                    qr.eq("type", 3);
                    break;
                default:
                    System.out.println("参数错误");
            }
        }
        return permissionDao.selectPage(page, qr);
    }

    @Override
    public List<Permission> findByTypeAndPid(int i, String permId) {
        QueryWrapper<Permission> qr = new QueryWrapper<>();
        qr.eq("type", i);
        qr.eq("pid", permId);
        return permissionDao.selectList(qr);
    }

    @Override
    public List<Permission> queryRolePerms(String roleId) {
        return permissionDao.queryRolePerms(roleId);
    }
}
