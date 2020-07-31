package pro.dengyi.syspermission.model.vo;

import javax.validation.constraints.NotNull;

/**
 * 权限树条件传输实体
 */

public class PermCondition {
    /**
     * 是否查询完整树
     * 1.true完整树包括所有菜单及按钮及api权限
     * 2.false不完整只查询菜单及子菜单
     */
    private boolean fullTree;
    /**
     * 用户类型1超管2企业管理员3普通
     */
    private Integer userType;
    /**
     * 用户ID，当用户是普通用户时必须传
     */
    private String userId;

    /**
     * 查询菜单树
     *
     * @param fullTree
     * @param userType
     * @param userId
     */
    public PermCondition(@NotNull boolean fullTree, @NotNull Integer userType, @NotNull String userId) {
        this.fullTree = fullTree;
        this.userType = userType;
        this.userId = userId;
    }

    /**
     * 管理员查询菜单树构造
     *
     * @param fullTree
     * @param userType
     */
    public PermCondition(@NotNull boolean fullTree, @NotNull Integer userType) {
        this.fullTree = fullTree;
        this.userType = userType;
    }

    /**
     * 查询按钮API构造
     *
     * @param userType
     */
    public PermCondition(@NotNull Integer userType) {
        this.userType = userType;
    }


    public boolean isFullTree() {
        return fullTree;
    }

    public void setFullTree(boolean fullTree) {
        this.fullTree = fullTree;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
