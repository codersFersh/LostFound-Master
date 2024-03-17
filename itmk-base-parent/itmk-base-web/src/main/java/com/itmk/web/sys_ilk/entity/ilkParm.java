package com.itmk.web.sys_ilk.entity;

import lombok.Data;

@Data
public class ilkParm {
    //当前第几页
    private Long currentPage;
    //没有查询的条数
    private Long pageSize;

    private String ilkName;
}
