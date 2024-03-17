package com.itmk.web.sys_lf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_lf.entity.LostFoundParm;
import com.itmk.web.sys_lf.entity.SysLf;
import com.itmk.web.sys_lf.service.SysLfService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/lost")
public class LostController {
    @Autowired
    private SysLfService sysLfService;

    //新增
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('sys:lost:add')")
    public ResultVo add(@RequestBody SysLf sysLf){
        //isIlk，1：失物，0：招领
        sysLf.setIsIlk("1");
        //失物状态：1：已领取，0：待领取
        sysLf.setIsLost("0");
        //        决定是否发布，2：审核通过，1：审核不通过，0:审核中。
        sysLf.setIsPass("0");
        if (sysLfService.save(sysLf)){
            return ResultUtils.success("新增成功！");
        }
        return ResultUtils.error("新增失败！");
    }

    //编辑
    @PostMapping("/edit")
//    @PreAuthorize("hasAuthority('sys:lost:edit')")
    public ResultVo edit(@RequestBody SysLf sysLf){
        sysLf.setMTime(new Date());
        if (sysLfService.updateById(sysLf)){
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败！");
    }

    //删除
    @DeleteMapping("/{lfId}")
//    @PreAuthorize("hasAuthority('sys:lost:delete')")
    public ResultVo delete(@PathVariable("lfId") Long lfId) {
            if (sysLfService.removeById(lfId)) {
                return ResultUtils.success("删除成功！");
            }
            return ResultUtils.error("删除失败！");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList(LostFoundParm parm){
        //构造分页对象
        IPage<SysLf> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysLf> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getLfName())){
            query.lambda().like(SysLf::getLfName,parm.getLfName());
        }
        if(StringUtils.isNotEmpty(parm.getLfType())){
            query.lambda().like(SysLf::getLfType,parm.getLfType());
        }
        //失物招领为1
        query.lambda().like(SysLf::getIsIlk,"1");
        query.lambda().orderByDesc(SysLf::getEst);
        IPage<SysLf> list = sysLfService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

}
