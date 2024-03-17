package com.itmk.web.sys_ilk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_ilk")
public class SysIlk {
    @TableId(type = IdType.AUTO)
    private Long ilkId;
    private String ilkName;
}
