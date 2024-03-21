package com.itmk.web.sys_info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_info.entity.AdminInfoParm;
import com.itmk.web.sys_info.entity.InfoUserParm;
import com.itmk.web.sys_info.entity.MyInfoParm;
import com.itmk.web.sys_info.entity.SysInfo;
import com.itmk.web.sys_info.service.SysInfoService;
import com.itmk.web.sys_lf.entity.SysLf;
import com.itmk.web.sys_lf.service.SysLfService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/info")
public class SysInfoController {
    @Autowired
    SysInfoService sysInfoService;

    @Autowired
    SysLfService sysLfService;

    @PostMapping("/add")
    public ResultVo add(@RequestBody SysInfo sysInfo){
        sysInfo.setInfoTime(new Date());
        sysInfo.setIsTrue("0");
        if (sysInfoService.save(sysInfo)){
            Long lfId = sysInfo.getLfId();
            // 根据 lfId 查询 SysLf 对象
            SysLf sysLf = sysLfService.getById(lfId);
            // 如果 isLost 不为空，则设置 isLost 为 2
            if (StringUtils.isNotEmpty(sysLf.getIsLost())) {
                sysLf.setIsLost("1");
            }

            // 如果 isFound 不为空，则设置 isFound 为 2
            if (StringUtils.isNotEmpty(sysLf.getIsFound())) {
                sysLf.setIsFound("1");
            };
            // 更新 SysLf 对象
            sysLfService.updateById(sysLf);

            return ResultUtils.success("发送成功！");
        }
        return ResultUtils.error("发送失败！");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList(InfoUserParm parm){
        //构造分页对象
        IPage<SysInfo> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysInfo> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getLfName())){
            query.lambda().like(SysInfo::getLfName,parm.getLfName());
        }
        if(StringUtils.isNotEmpty(parm.getLfUserid())){
            query.lambda().like(SysInfo::getLfUserid,parm.getLfUserid());
        }
        IPage<SysInfo> list = sysInfoService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
    @GetMapping("/myinfo")
    public ResultVo getList1(MyInfoParm parm){
        //构造分页对象
        IPage<SysInfo> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysInfo> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getLfName())){
            query.lambda().like(SysInfo::getLfName,parm.getLfName());
        }
        if(StringUtils.isNotEmpty(parm.getUserId())){
            query.lambda().like(SysInfo::getUserId,parm.getUserId());
        }
        IPage<SysInfo> list = sysInfoService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }


    //列表
    @GetMapping("/adminInfo")
    public ResultVo getList(AdminInfoParm parm){
        //构造分页对象
        IPage<SysInfo> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysInfo> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getLfName())){
            query.lambda().like(SysInfo::getLfName,parm.getLfName());
        }
        if(StringUtils.isNotEmpty(parm.getIsIlk())){
            query.lambda().like(SysInfo::getIsIlk,parm.getIsIlk());
        }
        IPage<SysInfo> list = sysInfoService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    //删除
    @DeleteMapping("/{infoId}")
    public ResultVo delete(@PathVariable("infoId") Long infoId) {
        SysInfo sysInfo = sysInfoService.getById(infoId);
        if (sysInfo == null) {
            return ResultUtils.error("删除失败！未找到对应的信息记录");
        }
        Long lfId = sysInfo.getLfId();
        if (sysInfoService.removeById(infoId)) {
            // 根据 lfId 查询 SysLf 对象
            SysLf sysLf = sysLfService.getById(lfId);
            // 如果 isLost 不为空，则设置 isLost 为 0
            if (StringUtils.isNotEmpty(sysLf.getIsLost())) {
                sysLf.setIsLost("0");
            }

            // 如果 isFound 不为空，则设置 isFound 为 0
            if (StringUtils.isNotEmpty(sysLf.getIsFound())) {
                sysLf.setIsFound("0");
            };

            sysLfService.updateById(sysLf);
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }

    @PostMapping("/edit")
//    @PreAuthorize("hasAuthority('sys:found:edit')")
    public ResultVo edit(@RequestBody SysInfo sysInfo){
        // 获取 lfId
        Long lfId = sysInfo.getLfId();
        if (sysInfoService.updateById(sysInfo)){
            // 根据 lfId 查询 SysLf 对象
            SysLf sysLf = sysLfService.getById(lfId);
            // 如果 isLost 不为空，则设置 isLost 为 2
            if (StringUtils.isNotEmpty(sysLf.getIsLost())) {
                sysLf.setIsLost("2");
            }

            // 如果 isFound 不为空，则设置 isFound 为 2
            if (StringUtils.isNotEmpty(sysLf.getIsFound())) {
                sysLf.setIsFound("2");
            };
            // 更新 SysLf 对象
            sysLfService.updateById(sysLf);
            return ResultUtils.success("回复成功！");
        }
        return ResultUtils.error("回复失败！");
    }

    @PostMapping("/editMyInfo")
    public ResultVo edit2(@RequestBody SysInfo sysInfo){
        if (sysInfoService.updateById(sysInfo)){
            return ResultUtils.success("修改成功！");
        }
        return ResultUtils.error("修改失败！");
    }
}
