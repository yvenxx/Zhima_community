package cn.yvenxx.zhima_community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserLikes {
    private String id;
    private String articleId;
    private String userId;
    private Integer status;
    private Long gmtCreate;
    private Long gmtModified;
}
