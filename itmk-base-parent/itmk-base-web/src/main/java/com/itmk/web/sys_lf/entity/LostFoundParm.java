package com.itmk.web.sys_lf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class LostParm {
    //当前第几页
    private Long currentPage;
    //没有查询的条数
    private Long pageSize;

    private String lfName;
    private String lfType;
}
