package com.itmk.web.sys_user.entity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class LoginVo {
    private Long userId;
    private String nickName;
    private String sex;
    private String token;
}
