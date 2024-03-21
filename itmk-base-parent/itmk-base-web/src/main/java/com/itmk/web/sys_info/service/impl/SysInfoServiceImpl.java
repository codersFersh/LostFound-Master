package com.itmk.web.sys_info.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.sys_info.entity.SysInfo;
import com.itmk.web.sys_info.mapper.SysInfoMapper;
import com.itmk.web.sys_info.service.SysInfoService;
import org.springframework.stereotype.Service;

@Service
public class SysInfoServiceImpl extends ServiceImpl<SysInfoMapper, SysInfo> implements SysInfoService {
}
