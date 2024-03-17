package com.itmk.system.sys_sld.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_sld")
public class SysSld {
    @TableId(type = IdType.AUTO)
        private Long sldId;
        private String sldName;
        private String sldType;
        private String sldImg;
        private String isEnable;
}
