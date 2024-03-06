package com.itmk.web.sys_role.controller;

import com.itmk.web.sys_role.entity.SelectItme;
import com.itmk.web.sys_role_menu.RoleMenu.RoleMenuService;
import com.itmk.web.sys_role_menu.entity.SaveMenuParm;
import com.itmk.web.sys_user_role.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_role.entity.RoleParm;
import com.itmk.web.sys_role.entity.SysRole;
import com.itmk.web.sys_role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/role")
@RestController
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    //新增
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
    public ResultVo add(@RequestBody SysRole sysRole){
        sysRole.setCreateTime(new Date());
        if (sysRoleService.save(sysRole)){
            return ResultUtils.success("新增成功！");
        }
        return ResultUtils.error("新增失败！");
    }

    //编辑
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    public ResultVo edit(@RequestBody SysRole sysRole){
        sysRole.setCreateTime(new Date());
        if (sysRoleService.updateById(sysRole)){
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败！");
    }

    //删除
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public ResultVo delete(@PathVariable("roleId") Long roleId) {
        if(sysUserRoleService.getById(roleId) != null){
            if (sysRoleService.removeById(roleId)) {
                return ResultUtils.success("删除成功！");
            }
            return ResultUtils.error("删除失败！");
        }
       return ResultUtils.error("该角色已有用户在使用不能删除");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList(RoleParm parm){
        //构造分页对象
        IPage<SysRole> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getRoleName())){
            query.lambda().like(SysRole::getRoleName,parm.getRoleName());
        }
        query.lambda().orderByDesc(SysRole::getCreateTime);
        IPage<SysRole> list = sysRoleService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
    //列表
    @GetMapping("/listUser")
    public ResultVo getListUser(RoleParm parm){
        //构造分页对象
        IPage<SysRole> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getRoleName())){
            query.lambda().like(SysRole::getRoleName,parm.getRoleName());
        }
        query.lambda().orderByDesc(SysRole::getCreateTime);
        IPage<SysRole> list = sysRoleService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }


    //角色下拉数据
    @GetMapping("/selectList")
    public ResultVo selectList(){
        List<SysRole> list = sysRoleService.list();
        //返回的值
        List<SelectItme> selectItmes = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .forEach(item ->{
                    SelectItme vo = new SelectItme();
                    vo.setCheck(false);
                    vo.setLabel(item.getRoleName());
                    vo.setValue(item.getRoleId());
                    selectItmes.add(vo);
                });
        return  ResultUtils.success("查询成功",selectItmes);
    }

    //保存角色菜单
    @PostMapping("/saveRoleMenu")
    @PreAuthorize("hasAuthority('sys:role:assign')")
    public ResultVo saveRoleMenu(@RequestBody SaveMenuParm parm){
        roleMenuService.saveRoleMenu(parm);
        return  ResultUtils.success("分配成功");
    }

}
