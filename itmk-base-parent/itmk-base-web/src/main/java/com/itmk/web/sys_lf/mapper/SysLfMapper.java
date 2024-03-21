package com.itmk.web.sys_lf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_lf.entity.SysLf;

public interface SysLfMapper extends BaseMapper<SysLf> {
    Integer countByIsPass();

}
