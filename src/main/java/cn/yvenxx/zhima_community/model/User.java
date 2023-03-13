package cn.yvenxx.zhima_community.model;

import lombok.Data;
import org.jetbrains.annotations.PropertyKey;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {

    private Integer id;
    private Integer accountId;
    private String userName;
    private String password;
    private String email;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}
