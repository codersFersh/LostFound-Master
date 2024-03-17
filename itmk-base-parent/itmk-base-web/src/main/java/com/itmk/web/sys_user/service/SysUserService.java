package com.itmk.system.sys_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.system.sys_menu.entity.AssignTreeParm;
import com.itmk.system.sys_menu.entity.AssignTreeVo;
import com.itmk.system.sys_user.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    //新增
    void saveUser(SysUser sysUser);
    void saveUserAdd(SysUser sysUser);

    //获取用户名
    SysUser findByUsername(String username);

    //编辑
    void editUser(SysUser sysUser);
    void editUser1(SysUser sysUser);
    //删除用户
    void deleteUser(Long userId);

    //查询菜单树
    AssignTreeVo getAssignTree(AssignTreeParm parm);

    //根据用户名查用户
    SysUser loadUser(String username);
}

