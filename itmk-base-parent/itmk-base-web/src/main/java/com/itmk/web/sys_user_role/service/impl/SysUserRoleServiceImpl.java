    package com.itmk.web.sys_user_role.service.impl;

    import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
    import com.itmk.web.sys_user_role.entity.SysUserRole;
    import com.itmk.web.sys_user_role.mapper.SysUserRoleMapper;
    import com.itmk.web.sys_user_role.service.SysUserRoleService;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;


    @Service
    public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

        @Transactional
        public Integer selectById(Long roleId) {
            return baseMapper.selectById(roleId);
        }

        @Override
        public SysUserRole getByUserId(Long userId) {
            return baseMapper.getByUserId(userId);
        }
    }