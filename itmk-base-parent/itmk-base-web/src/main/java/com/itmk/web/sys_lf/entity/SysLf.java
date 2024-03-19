package com.itmk.web.sys_lf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_lf")
public class SysLf {
    @TableId(type = IdType.AUTO)
    private Long lfId;
    private Long userId;
    private String isIlk;
    private String lfType;
    private String lfName;
    private String lfAddress;
    private String lfImg;
    private String lfContent;
    private String lfUsername;
    private String lfPhone;
    private String isPass;
    private String ispassCause;
    private String isLost;
    private String isFound;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lfTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date est;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mtime;

}
