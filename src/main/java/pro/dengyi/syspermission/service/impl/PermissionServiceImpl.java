package pro.dengyi.syspermission.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pro.dengyi.syspermission.common.exception.BusinessException;
import pro.dengyi.syspermission.common.response.BaseResponseEnum;
import pro.dengyi.syspermission.dao.*;
import pro.dengyi.syspermission.model.*;
import pro.dengyi.syspermission.model.request.PermissionPageQueryVo;
import pro.dengyi.syspermission.model.request.PermissionRequestVo;
import pro.dengyi.syspermission.model.vo.PermCondition;
import pro.dengyi.syspermission.service.PermissionService;
import pro.dengyi.syspermission.utils.UserHolder;

import java.util.ArrayList;
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

    @Autowired
    private RolePermissionDao rolePermissionDao;


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

    /**
     * 删除权限
     * 需要注意的问题：
     * 1. 当权限包含子权限的时候不能删除
     * 2. 当不包含子权限时，如果删除，需要同时将角色权限关联表中数据同时删除
     *
     * @param permissionId
     */
    @Override
    @Transactional
    public void deleteById(String permissionId) {
        //基础数据不能删除
        QueryWrapper<Permission> q = new QueryWrapper<>();
        q.eq("can_delete", false);
        q.eq("id", permissionId);
        Permission permissionCanDeleted = permissionDao.selectOne(q);
        if (permissionCanDeleted != null) {
            throw new BusinessException(BaseResponseEnum.FAIL);
        }
        //权限被使用或者有子权限均不能删除
        QueryWrapper<RolePermission> qr = new QueryWrapper<>();
        qr.eq("permission_id", permissionId);
        RolePermission rolePermissionMiddle = rolePermissionDao.selectOne(qr);
        QueryWrapper<Permission> qrr = new QueryWrapper<>();
        qrr.eq("pid", permissionId);
        Permission permissionSelected = permissionDao.selectOne(qrr);
        if (rolePermissionMiddle != null || permissionSelected != null) {
            //其中任意不为null均已被使用则不能删除
            throw new BusinessException(BaseResponseEnum.FAIL);
        }
        //删除
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
    public List<Permission> getPermTree() {
        SystemUser systemUser = UserHolder.getUser();
        List<Permission> permTree = getPermTree(new PermCondition(true, systemUser.getIsSassAdmin() ? 1 : systemUser.getIsCoAdmin() ? 2 : 3));
        return permTree;
    }

    /**
     * 根据条件查询权限树
     *
     * @param condition
     * @return
     */
    @Override
    public List<Permission> getPermTree(PermCondition condition) {
        Integer userType = condition.getUserType();
        QueryWrapper<Permission> qr = new QueryWrapper<>();
        List<Permission> permTree = null;
        switch (userType) {
            case 1:
                //超管,查询所有
                qr.isNull("pid");
                permTree = permissionDao.selectList(qr);
                break;
            case 2:
                //企业管理员,非系统管理员无须考虑api权限
                qr.isNull("pid");
                qr.eq("en_visible", true);
                permTree = permissionDao.selectList(qr);
                break;
            case 3:
                //普通用户
                permTree = permissionDao.selectFirstByUserId(condition.getUserId());
                break;
            default:
                throw new BusinessException(BaseResponseEnum.FAIL);
        }
        //迭代所有
        iterateChildren(permTree, condition.isFullTree());

        return permTree;
    }

    @Override
    public List<String> getPermButtons(PermCondition condition) {
        Integer userType = condition.getUserType();
        QueryWrapper<Permission> qr = new QueryWrapper<>();
        List<Permission> permList = null;
        switch (userType) {
            case 1:
                //超管,查询所有
                qr.eq("type", 2);
                permList = permissionDao.selectList(qr);
                break;
            case 2:
                //企业管理员
                qr.eq("type", 2);
                qr.eq("en_visible", true);
                permList = permissionDao.selectList(qr);
                break;
            case 3:
                //普通用户
                permList = permissionDao.selectButtonsByUserId(condition.getUserId());
                break;
            default:
                throw new BusinessException(BaseResponseEnum.FAIL);
        }
        List<String> permButtons = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permList)) {
            for (Permission permission : permList) {
                permButtons.add(permission.getCode());
            }
        }
        return permButtons;
    }

    @Override
    public List<String> getRolePermIds(String roleId) {
        List<String> permIds = new ArrayList<>();
        QueryWrapper<RolePermission> qr = new QueryWrapper<>();
        qr.eq("role_id", roleId);
        List<RolePermission> rolePermissions = rolePermissionDao.selectList(qr);
        if (!CollectionUtils.isEmpty(rolePermissions)) {
            for (RolePermission rolePermission : rolePermissions) {
                permIds.add(rolePermission.getPermissionId());
            }
        }
        return permIds;
    }

    /**
     * 递归迭代所有字权限
     *
     * @param fatherPermissionList 父权限集合
     * @param fullTree             是否迭代完全树，true迭代菜单、按钮、权限，false只迭代菜单
     */
    private void iterateChildren(List<Permission> fatherPermissionList, Boolean fullTree) {
        if (!CollectionUtils.isEmpty(fatherPermissionList)) {
            for (Permission fatherPermission : fatherPermissionList) {
                //查询二级
                QueryWrapper<Permission> qrr = new QueryWrapper<>();
                qrr.eq("pid", fatherPermission.getId());
                List<Permission> sonPermissionList = permissionDao.selectList(qrr);
                if (!CollectionUtils.isEmpty(sonPermissionList)) {
                    //完全迭代
                    if (fullTree) {
                        fatherPermission.setChildren(sonPermissionList);
                    }
                    for (Permission sonPermission : sonPermissionList) {
                        //非完全迭代只添加菜单
                        if (sonPermission.getType() == 1) {
                            if (!fullTree) {
                                List<Permission> children = fatherPermission.getChildren();
                                children.add(sonPermission);
                                fatherPermission.setChildren(children);
                            }
                            //如果子权限也是菜单，那么继续递归
                            iterateChildren(sonPermissionList, fullTree);
                        }

                    }
                }
            }
        }
    }

}
