package cn.yvenxx.zhima_community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {

    private Integer id;
    private String role;
    private String userName;
    private String password;
    private String email;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}
