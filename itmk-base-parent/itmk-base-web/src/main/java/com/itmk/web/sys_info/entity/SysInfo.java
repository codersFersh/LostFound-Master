package com.itmk.web.sys_info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_info")
public class SysInfo {
    @TableId(type = IdType.AUTO)
    private Long infoId;
    private Long userId;
    private String nickName;
    private String infoPhone;
    private String infoContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date infoTime;
    private Long lfId;
    private Long isIlk;
    private Long lfUserid;
    private String lfName;
    private String lfImg;
    private String lfUsername;
    private String isTrue;
}
