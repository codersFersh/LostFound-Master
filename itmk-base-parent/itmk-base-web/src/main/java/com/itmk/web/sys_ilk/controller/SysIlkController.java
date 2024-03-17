package com.itmk.web.sys_ilk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_ilk.entity.SysIlk;
import com.itmk.web.sys_ilk.entity.ilkParm;
import com.itmk.web.sys_ilk.service.SysIlkService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ilk")
public class SysIlkController {
    @Autowired
    private SysIlkService sysIlkService;

    //新增
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:ilk:add')")
    public ResultVo add(@RequestBody SysIlk sysIlk){
        if (sysIlkService.save(sysIlk)){
            return ResultUtils.success("新增成功！");
        }
        return ResultUtils.error("新增失败！");
    }

    //编辑
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('sys:ilk:edit')")
    public ResultVo edit(@RequestBody SysIlk sysIlk){
        if (sysIlkService.updateById(sysIlk)){
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败！");
    }

    //删除
    @DeleteMapping("/{likId}")
    @PreAuthorize("hasAuthority('sys:ilk:delete')")
    public ResultVo delete(@PathVariable("likId") Long likId) {
        if (sysIlkService.removeById(likId)) {
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList(ilkParm parm){
        //构造分页对象
        IPage<SysIlk> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysIlk> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getIlkName())){
            query.lambda().like(SysIlk::getIlkName,parm.getIlkName());
        }
        IPage<SysIlk> list = sysIlkService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
     @GetMapping("/likAll")
    public ResultVo list(){
        List<SysIlk> list = sysIlkService.list();
        return ResultUtils.success("查询成功",list);
    }



}
