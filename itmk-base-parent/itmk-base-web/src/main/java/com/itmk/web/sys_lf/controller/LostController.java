package com.itmk.web.sys_lf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_lf.entity.LostFoundParm;
import com.itmk.web.sys_lf.entity.PassLostParm;
import com.itmk.web.sys_lf.entity.SysLf;
import com.itmk.web.sys_lf.service.SysLfService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/lost")
public class LostController {
    @Autowired
    private SysLfService sysLfService;

    //新增
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('sys:userlost:add')")
    public ResultVo add(@RequestBody SysLf sysLf){
        //isIlk，1：失物，0：招领
        sysLf.setIsIlk("1");
        //失物状态：1：已领取，0：待领取
        sysLf.setIsLost("0");
        //        决定是否发布，2：审核通过，1：审核不通过，0:审核中。
        sysLf.setIsPass("0");
        sysLf.setEst(new Date());
        if (sysLfService.save(sysLf)){
            return ResultUtils.success("新增成功！");
        }
        return ResultUtils.error("新增失败！");
    }

    //编辑
    @PostMapping("/edit")
//    @PreAuthorize("hasAuthority('sys:userlost:edit')")
    public ResultVo edit(@RequestBody SysLf sysLf){
        sysLf.setMtime(new Date());
        if (sysLfService.updateById(sysLf)){
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败！");
    }

    //删除
    @DeleteMapping("/{lfId}")
//    @PreAuthorize("hasAuthority('sys:userlost:delete')")
    public ResultVo delete(@PathVariable("lfId") Long lfId) {
            if (sysLfService.removeById(lfId)) {
                return ResultUtils.success("删除成功！");
            }
            return ResultUtils.error("删除失败！");
    }

    // 列表
    @GetMapping("/list")
    public ResultVo getList(LostFoundParm parm) {
        // 构造分页对象
        IPage<SysLf> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        // 构造查询条件
        QueryWrapper<SysLf> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getLfName())) {
            query.lambda().like(SysLf::getLfName, parm.getLfName());
        }
        if (StringUtils.isNotEmpty(parm.getLfType())) {
            query.lambda().like(SysLf::getLfType, parm.getLfType());
        }
        // 失物招领为1
        query.lambda().like(SysLf::getIsIlk, "1");
        //审核通过才显示
        query.lambda().like(SysLf::getIsPass, "2");
        //显示未寻回的数据
        query.lambda().like(SysLf::getIsLost,"0");
        query.lambda().orderByDesc(SysLf::getLfTime);
        IPage<SysLf> list = sysLfService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    @GetMapping("/By/{lfId}")
    public ResultVo byId(@PathVariable("lfId") Long lfId){
        SysLf sysLf = sysLfService.getById(lfId);
        if (sysLf != null) {
            return ResultUtils.success("查询成功！", sysLf);
        } else {
            return ResultUtils.error("查询失败！");
        }
    }


    //管理员编辑：管理审核状态
    @PostMapping("/admin/status")
//    @PreAuthorize("hasAuthority('sys:adminlost:edit')")
    public ResultVo edit2(@RequestBody SysLf sysLf){
        if (sysLfService.updateById(sysLf)){
            return ResultUtils.success("审核成功！");
        }
        return ResultUtils.error("审核失败！");
    }


    //删除
    @DeleteMapping("/admin/{lfId}")
//    @PreAuthorize("hasAuthority('sys:adminlost:delete')")
    public ResultVo delete2(@PathVariable("lfId") Long lfId) {
        if (sysLfService.removeById(lfId)) {
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }


    // 列表
    @GetMapping("/isPassList")
    public ResultVo List(PassLostParm parm) {
        // 构造分页对象
        IPage<SysLf> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        // 构造查询条件
        QueryWrapper<SysLf> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getLfName())) {
            query.lambda().like(SysLf::getLfName, parm.getLfName());
        }
        if (StringUtils.isNotEmpty(parm.getLfType())) {
            query.lambda().like(SysLf::getLfType, parm.getLfType());
        }
        if (StringUtils.isNotEmpty(parm.getIsPass())) {
            query.lambda().like(SysLf::getIsPass, parm.getIsPass());
        }
        if (StringUtils.isNotEmpty(parm.getIsLost())) {
            query.lambda().like(SysLf::getIsLost, parm.getIsLost());
        }
        // 失物招领为1
        query.lambda().like(SysLf::getIsIlk, "1");
        query.lambda().orderByDesc(SysLf::getLfTime);
        IPage<SysLf> list = sysLfService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }


}
