package com.itmk.system.sys_user.entity;
import lombok.Data;


@Data
public class LoginVo {
    private Long userId;
    private String nickName;
    private String sex;
    private String token;
}
