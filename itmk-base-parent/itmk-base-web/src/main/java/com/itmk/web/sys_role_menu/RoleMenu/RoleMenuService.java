package com.itmk.web.sys_role_menu.RoleMenu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_role_menu.entity.RoleMenu;
import com.itmk.web.sys_role_menu.entity.SaveMenuParm;


public interface RoleMenuService extends IService<RoleMenu> {
    void saveRoleMenu(SaveMenuParm parm);
}