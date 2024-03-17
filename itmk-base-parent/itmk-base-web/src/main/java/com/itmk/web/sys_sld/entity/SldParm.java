package com.itmk.system.sys_sld.entity;

import lombok.Data;

@Data
public class SldParm {
    //当前第几页
    private Long currentPage;
    //没有查询的条数
    private Long pageSize;

    private String sldName;

}
