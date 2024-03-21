package com.itmk.web.sys_user.entity;
import lombok.Data;


@Data
public class LoginVo {
    private Long userId;
    private String nickName;
    private String sex;
    private String token;
    private Long roleId;
}
