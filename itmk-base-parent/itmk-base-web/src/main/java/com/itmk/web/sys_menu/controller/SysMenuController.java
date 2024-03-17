package com.itmk.web.sys_menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_menu.entity.MakeMenuTree;
import com.itmk.web.sys_menu.entity.SysMenu;
import com.itmk.web.sys_menu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RequestMapping("/menu")
@RestController
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    //新增
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public ResultVo add(@RequestBody SysMenu sysMenu){
        sysMenu.setCreateTime(new Date());
        if(sysMenuService.save(sysMenu)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    public ResultVo edit(@RequestBody SysMenu sysMenu){
        sysMenu.setUpdateTime(new Date());
        if(sysMenuService.updateById(sysMenu)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public ResultVo delete(@PathVariable("menuId") Long menuId){
        //如果存在下级菜单，不能删除
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().eq(SysMenu::getParentId,menuId);
        List<SysMenu> list = sysMenuService.list(query);
        if (list.size() > 0){
            return ResultUtils.error("该菜单存在下级菜单不能删除！");
        }
        if(sysMenuService.removeById(menuId)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList(){
        //排序
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(SysMenu::getOrderNum);
        //查询出所有的菜单
        List<SysMenu> list = sysMenuService.list(query);
        //组装树数据
        List<SysMenu> menuList = MakeMenuTree.makeTree(list, 0L);
        return ResultUtils.success("查询成功",menuList);
    }

    //上级菜单
    @GetMapping("/getParent")
    public ResultVo getParent(){
        List<SysMenu> list = sysMenuService.getParent();
        return ResultUtils.success("查询成功",list);
    }
}