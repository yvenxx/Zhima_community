package cn.yvenxx.zhima_community.utils;

import cn.yvenxx.zhima_community.service.ESArticleService;
import cn.yvenxx.zhima_community.service.UserLikesService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class CronUtil extends QuartzJobBean {

    @Autowired
    private UserLikesService userLikesService;
    @Autowired
    ESArticleService esArticleService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 执行的定时任务
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("LikeTask-------- {}", sdf.format(new Date()));

        //将 Redis 里的点赞信息同步到数据库里
        userLikesService.transLikedFromRedis2DB();
        userLikesService.transLikedCountFromRedis2DB();

        //定时更新Es
        esArticleService.updateArticleToES();
    }
}
