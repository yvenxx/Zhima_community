package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.service.LikeRedisService;
import cn.yvenxx.zhima_community.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LikeController {
    @Autowired
    private LikeRedisService likeRedisService;

    @PostMapping("like")
    public R likeInfo(String articleId,String userId){
        likeRedisService.saveLikedToRedisAndIncrementCount(articleId,userId);
        return R.succ(null);
    }

    @PostMapping("unlike")
    public R unlike(String articleId,String userId){
        likeRedisService.unlikeFromRedis(articleId,userId);
        return R.succ(null);
    }
}
