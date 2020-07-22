# 经典RBAC权限系统扩展系统

## RBAC是什么？

RBAC是基于角色的访问控制也就是权限的简称

> Role-Based Access Control

## 具体怎么实现？

1. 根据用户的ID查询对应的角色集合
2. 通过遍历角色集合获取到每个角色
3. 通过每个角色ID查询到对应的权限
4. 通过权限判断用户是否有权限

## 传统RBAC有什么缺点？

现如今前后端分离开发，除了菜单、按钮权限。其实也应该做API权限。但是传统RBAC
