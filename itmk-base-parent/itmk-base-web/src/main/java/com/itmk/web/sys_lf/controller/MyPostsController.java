package com.itmk.web.sys_lf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_lf.entity.MyPostsParm;
import com.itmk.web.sys_lf.entity.SysLf;
import com.itmk.web.sys_lf.service.SysLfService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/my")
public class MyPostsController {

   @Autowired
    private SysLfService sysLfService;

   @GetMapping("/list")
   public ResultVo List(MyPostsParm parm) {
       // 构造分页对象
       IPage<SysLf> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
       // 构造查询条件
       QueryWrapper<SysLf> query = new QueryWrapper<>();
       if (StringUtils.isNotEmpty(parm.getLfName())) {
           query.lambda().like(SysLf::getLfName, parm.getLfName());
       }
       if (StringUtils.isNotEmpty(parm.getIsFound())) {
           query.lambda().like(SysLf::getIsFound, parm.getIsFound());
       }
       if (StringUtils.isNotEmpty(parm.getIsPass())) {
           query.lambda().like(SysLf::getIsPass, parm.getIsPass());
       }
       if (StringUtils.isNotEmpty(parm.getIsLost())) {
           query.lambda().like(SysLf::getIsLost, parm.getIsLost());
       }
       if (StringUtils.isNotEmpty(parm.getUserId())) {
           query.lambda().like(SysLf::getUserId, parm.getUserId());
       }
       query.lambda().orderByDesc(SysLf::getLfTime);
       IPage<SysLf> list = sysLfService.page(page, query);
       return ResultUtils.success("查询成功", list);
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


}
