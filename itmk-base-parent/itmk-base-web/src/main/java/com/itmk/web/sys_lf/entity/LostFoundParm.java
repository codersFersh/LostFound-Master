package com.itmk.web.sys_lf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class LostFoundParm {
    //当前第几页
    private Long currentPage;
    //没有查询的条数
    private Long pageSize;

    private String lfName;
    private String lfType;

}
