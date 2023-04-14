package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.service.LikeRedisService;
import cn.yvenxx.zhima_community.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    @Autowired
    private LikeRedisService likeRedisService;

    @PostMapping("like")
    public R likeInfo(String articleId,String userId){

        return R.succ(null);
    }

}
