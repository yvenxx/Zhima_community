package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.model.UserLikes;
import cn.yvenxx.zhima_community.service.LikeRedisService;
import cn.yvenxx.zhima_community.service.UserLikesService;
import cn.yvenxx.zhima_community.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("article")
public class LikeController {
    @Autowired
    private LikeRedisService likeRedisService;
    @Autowired
    private UserLikesService userLikesService;

    @PostMapping("like")
    public R likeInfo(String articleId,String userId){
        Integer status = likeRedisService.getLikeStatus(articleId, userId);
        if (status==1){
            return R.fail("已经点过赞了");
        }
        likeRedisService.saveLikedToRedisAndIncrementCount(articleId,userId);
        return R.succ(null);
    }

    @PostMapping("unlike")
    public R unlike(String articleId,String userId){
        // redis
        Integer status = likeRedisService.getLikeStatus(articleId, userId);
        if (status==0){
            return R.fail("Error,本来就没点赞");
        }
        // mysql
        likeRedisService.unlikeFromRedis(articleId,userId);
        return R.succ(null);
    }

    @GetMapping("status")
    public R status(String articleId,String userId){
        // redis
        Integer status = likeRedisService.getLikeStatus(articleId, userId);
        if (status!=-1){
            return R.succ(status);
        }
        // mysql
        UserLikes userlikes = userLikesService.getLikeStatus(articleId, userId);

        //缓存自己对于这个文章的点赞状态
        likeRedisService.insertLike(articleId,userId,userlikes==null?0:userlikes.getStatus());
        return R.succ(userlikes==null?0:userlikes.getStatus());
    }
}
