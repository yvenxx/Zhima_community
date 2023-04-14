package cn.yvenxx.zhima_community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLikesDTO {
    //被点赞的内容的id
    private String articleId;
    //点赞的用户id
    private String likeUserId;
    private Integer status;
    private LocalDateTime updateTime;
}
