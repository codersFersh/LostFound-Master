package com.itmk.web.sys_user_role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_user_role.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;


public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
   Integer selectById(@Param("roleId") Long roleId);

   SysUserRole getByUserId(Long userId);
}
