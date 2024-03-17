package com.itmk.system.sys_role_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.system.sys_role_menu.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    //报存角色菜单
    boolean saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds")List<Long> menuIds);
}