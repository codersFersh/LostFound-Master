package com.itmk.web.sys_info.entity;

import lombok.Data;

@Data
public class MyInfoParm {
    //当前第几页
    private Long currentPage;
    //没有查询的条数
    private Long pageSize;

    private String lfName;
    private String isIlk;
    private String userId;
}
