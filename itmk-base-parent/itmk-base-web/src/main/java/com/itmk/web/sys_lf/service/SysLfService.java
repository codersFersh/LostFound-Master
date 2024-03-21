package com.itmk.web.sys_lf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_lf.entity.SysLf;


public interface SysLfService extends IService<SysLf> {
    Integer countByIsPass();
}
