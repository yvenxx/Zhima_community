package cn.yvenxx.zhima_community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeCountDTO {
    private String articleId;
    private Integer likeCount;
}