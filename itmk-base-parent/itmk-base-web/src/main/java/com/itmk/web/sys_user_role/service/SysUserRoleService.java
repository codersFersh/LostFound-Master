package com.itmk.web.sys_user_role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_user_role.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;


public interface SysUserRoleService extends IService<SysUserRole> {
    //保存角色
//    void saveRole(SysUserRole sysUserRole);
    Integer selectById(@Param("roleId") Long roleId);

    SysUserRole getByUserId(@Param("userId") Long userId);
}