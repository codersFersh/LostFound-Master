package com.itmk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 封装返回值类型
 */


@Data   //自动生成get和set方法
@AllArgsConstructor     //自动生成构造函数
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}