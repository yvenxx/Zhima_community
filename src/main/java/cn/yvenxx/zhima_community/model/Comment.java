package cn.yvenxx.zhima_community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class Comment {
    private Integer id;
    private String username;
    private Integer userId;
    private String content;
    private Date createTime;
    private Integer blogId;
    private Integer parentId;
    private String target;
}
