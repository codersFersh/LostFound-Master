package com.itmk.web.sys_lf.entity;

import lombok.Data;

@Data
public class MyPostsParm {
    //当前第几页
    private Long currentPage;
    //没有查询的条数
    private Long pageSize;

    private String userId;
    private String isIlk;
    private String lfName;
    private String isPass;
    private String isFound;
    private String isLost;
}
