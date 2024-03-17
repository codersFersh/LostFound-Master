package com.itmk.system.sys_role_menu.RoleMenu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.sys_role_menu.RoleMenu.RoleMenuService;
import com.itmk.system.sys_role_menu.entity.RoleMenu;
import com.itmk.system.sys_role_menu.entity.SaveMenuParm;
import com.itmk.system.sys_role_menu.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    @Transactional
    public void saveRoleMenu(SaveMenuParm parm) {
        //先删除
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId,parm.getRoleId());
        this.baseMapper.delete(query);
        //再保存
        this.baseMapper.saveRoleMenu(parm.getRoleId(),parm.getList());
    }
}
