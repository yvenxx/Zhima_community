package cn.yvenxx.zhima_community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    @NotNull
    private String username;
    @NotNull
    private Integer userId;
    @NotNull
    private String content;
    private LocalDateTime createTime;
    @NotNull
    private Integer blogId;
    private Integer parentId;
    private String target;
    @Transient
    private List<Comment> children;
}
