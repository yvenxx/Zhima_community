package cn.yvenxx.zhima_community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Article {
    private Integer aid;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String category;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;

}

