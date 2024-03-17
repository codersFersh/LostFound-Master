package com.itmk.system.sys_role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.sys_role.entity.SysRole;
import com.itmk.system.sys_role.mapper.SysRoleMapper;
import com.itmk.system.sys_role.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
