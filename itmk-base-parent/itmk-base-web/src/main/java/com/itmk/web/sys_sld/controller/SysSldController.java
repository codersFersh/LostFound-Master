package com.itmk.web.sys_sld.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_sld.entity.SldParm;
import com.itmk.web.sys_sld.entity.SysSld;
import com.itmk.web.sys_sld.service.SysSldService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sld")
@RestController
public class SysSldController {

    @Autowired
    private SysSldService sysSldService;

    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('sys:sld:add')")
    public ResultVo add(@RequestBody SysSld sysSld){
        if(sysSldService.save(sysSld)){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }

    @PostMapping("/edit")
//    @PreAuthorize("hasAuthority('sys:sld:edit')")
    public ResultVo enit(@RequestBody SysSld sysSld) {
        if (sysSldService.updateById(sysSld)) {
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败！");
    }

    @DeleteMapping("/{del}")
//    @PreAuthorize("hasAuthority('sys:sld:delete')")
    public ResultVo delete(@PathVariable("sldId") Long sldId){
        if(sysSldService.removeById(sldId)){
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }

    @GetMapping("/list")
    public ResultVo list(SldParm parm){
        //构造分页对象
        IPage<SysSld> page  = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysSld> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getSldName())){
            query.lambda().like(SysSld::getSldName,parm.getSldName());
        }
        IPage<SysSld> list = sysSldService.page(page,query);
        return ResultUtils.success("查询成功",list);
    }

}
