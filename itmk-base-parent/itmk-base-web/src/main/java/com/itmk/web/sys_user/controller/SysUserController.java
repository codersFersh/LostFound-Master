package com.itmk.system.sys_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.system.sys_user.entity.*;
import com.itmk.system.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/user")
@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //新增
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('sys:user:add')")
    public ResultVo add(@RequestBody SysUser sysUser){
        sysUser.setCreateTime(new Date());
        SysUser existingUser = sysUserService.findByUsername(sysUser.getUsername());
        if(existingUser == null){
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            sysUser.setIsUser("1");
            sysUserService.saveUserAdd(sysUser);
            return ResultUtils.success("注册成功!");
        }
        return ResultUtils.error("新增失败，同名账号已注册!");
    }

    //编辑
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public ResultVo edit(@RequestBody SysUser sysUser){
//        sysUser.setUpdateTime(new Date());
//        sysUserService.editUser(sysUser);
//        return ResultUtils.success("编辑成功!");

        sysUser.setCreateTime(new Date());
        SysUser existingUser = sysUserService.findByUsername(sysUser.getUsername());
        if(existingUser == null){
            sysUserService.editUser1(sysUser);
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败，已存在同名账号!");
    }

    //删除
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResultVo delete(@PathVariable("userId") Long userId){
        sysUserService.deleteUser(userId);
        return ResultUtils.success("删除成功!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(SysUserPage parm){
        //构造分页对象
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNickName())){
            query.lambda().like(SysUser::getNickName,parm.getNickName());
        }
        if(StringUtils.isNotEmpty(parm.getPhone())){
            query.lambda().like(SysUser::getPhone,parm.getPhone());
        }
        query.lambda().like(SysUser::getIsUser,"1");
        query.lambda().orderByDesc(SysUser::getCreateTime);
        //查询列表
        IPage<SysUser> list = sysUserService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }




    //重置密码
    @PostMapping("/resetPassword")
    @PreAuthorize("hasAuthority('sys:user:reset')")
    public ResultVo resetPassword(@RequestBody SysUser sysUser){
        UpdateWrapper<SysUser> query = new UpdateWrapper<>();
        query.lambda().eq(SysUser::getUserId,sysUser.getUserId())
                .set(SysUser::getPassword,passwordEncoder.encode("123"));
        if(sysUserService.update(query)){
            return ResultUtils.success("密码重置成功!");
        }
        return ResultUtils.error("密码重置失败!");
    }



    //修改密码
    @PostMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody UpdatePasswordParm parm){
        SysUser user = sysUserService.getById(parm.getUserId());
//        if(!parm.getOldPassword().equals(user.getPassword())){
//            return ResultUtils.error("原密码不正确!");
//        }
        if(!passwordEncoder.matches(parm.getOldPassword(), user.getPassword())){
            return ResultUtils.error("原密码不正确!");
        }
        //更新条件
        UpdateWrapper<SysUser> query = new UpdateWrapper<>();
        query.lambda().set(SysUser::getPassword,parm.getPassword())
                .eq(SysUser::getUserId,parm.getUserId());
        if(sysUserService.update(query)){
            return ResultUtils.success("密码修改成功!");
        }
        return ResultUtils.error("密码修改失败!");
    }




}
