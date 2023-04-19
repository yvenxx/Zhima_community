package cn.yvenxx.zhima_community.service.impl;


import cn.yvenxx.zhima_community.dto.UserLikeCountDTO;
import cn.yvenxx.zhima_community.dto.UserLikesDTO;
import cn.yvenxx.zhima_community.mapper.ArticleMapper;
import cn.yvenxx.zhima_community.mapper.UserLikesMapper;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.model.UserLikes;
import cn.yvenxx.zhima_community.service.LikeRedisService;
import cn.yvenxx.zhima_community.service.UserLikesService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class UserLikesServiceImpl implements UserLikesService {
    @Autowired
    UserLikesMapper userLikesMapper;
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    LikeRedisService likeRedisService;


    @Override
    public UserLikes getLikeStatus(String articleId, String userId) {
        UserLikes userLikes = userLikesMapper.selectByUserIdAndArticleId(articleId, userId);
        return userLikes;
    }

    @Override
    public Boolean save(UserLikes userLike) {
        int rows = userLikesMapper.insert(userLike);
        return rows > 0;
    }

    @Override
    public Boolean update(UserLikes userLike) {
        int rows = userLikesMapper.update(userLike);
        return rows > 0;
    }

    @Override
    public Page<UserLikes> getLikedListByArticleId(String articleId, int pageNum, int pageSize) {
        // 分页获取喜欢列表信息
        PageHelper.startPage(pageNum, pageNum);
        Page<UserLikes> list = userLikesMapper.selectAllByArticleId(articleId);
        log.info("获得内容的id查询点赞列表（即查询都有谁给这个内容点赞过）");

        return list;
    }

    @Override
    public Page<UserLikes> getLikedListByLikeUserId(String likeUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<UserLikes> userLikes = userLikesMapper.selectAllByUserId(likeUserId);
        log.info("获取点赞人的id查询点赞列表（即查询这个人都给哪些内容点赞过）");
        return userLikes;
    }

    @Override
    public UserLikes getByArticleIdAndLikeUserId(String articleId, String likeUserId) {
        try{
            UserLikes userLikes = userLikesMapper.selectByUserIdAndArticleId(articleId,likeUserId);
            log.info("通过被点赞人和点赞人id查询是否存在点赞记录");
            return userLikes;
        }catch (Exception e){
            log.info("当前查询的被点赞人和点赞人id不存在点赞记录");
            return null;
        }
    }

    @Override
    public void transLikedFromRedis2DB() {
        // 批量获取缓存中的点赞数据
        List<UserLikesDTO> list = likeRedisService.getLikedDataFromRedis();
        if (CollectionUtils.isEmpty(list))// 为空，不写入
            return;
        for (UserLikesDTO item: list){
            UserLikes userLikes = getByArticleIdAndLikeUserId(item.getArticleId(), item.getLikeUserId());// 在数据库中查询
            if (userLikes == null) {
                // 无记录，新增
                if(!save(userLikesDTOtoUserLikes(item))){
                    log.info("新增点赞数据失败！");
                    return;
                    // System.out.println("缓存记录写入数据库失败！请重试");
                }
            }else {
                // 有记录，更新
                // 判断数据库中点赞状态与缓存中点赞状态一致性
                if (userLikes.getStatus()==item.getStatus()){
                    // 一致，无需持久化，点赞数量-1
                    likeRedisService.decrementLikedCount(item.getArticleId(), -1);
                }else{// 不一致
                    if (userLikes.getStatus()== 1){// 在数据库中已经是点赞，则取消点赞，同时记得redis中的count-1
                        // 之前是点赞，现在改为取消点赞
                        // 1.设置更改status
                        // 2. count-1
                        userLikes.setStatus(0);
//                        update(userLikes);
                        likeRedisService.decrementLikedCount(item.getArticleId(), -1);

                    } else if (userLikes.getStatus()== 0) {// 未点赞，则点赞，修改点赞状态和点赞数据+1
                        userLikes.setStatus(1);
                        likeRedisService.decrementLikedCount(item.getArticleId(), -1);
//                        update(userLikes);
                    }
                    userLikes.setGmtModified(System.currentTimeMillis());
                    if(!update(userLikes)){// 更新点赞数据
                        log.info("更新点赞数据失败！");
                        return;
                        // System.out.println("缓存记录更新数据库失败！请重试");
                    }
                }
            }
        }
    }

    @Override
    public void transLikedCountFromRedis2DB() {
        // 获取缓存中内容的点赞数列表
        List<UserLikeCountDTO> list = likeRedisService.getLikedCountFromRedis();
        if (CollectionUtils.isEmpty(list))// 为空，不写入
            return;
        for (UserLikeCountDTO item: list){

            Article article = articleMapper.getArticleByid(Integer.parseInt(item.getArticleId()));

            if (article != null) {// 新增点赞数
                Integer likeCount = article.getLikeCount() + item.getLikeCount();
                System.out.println("内容id不为空，更新内容点赞数量");
                article.setLikeCount(likeCount);

                int rows = articleMapper.update(article);

                if (rows > 0) {
                    System.out.println("成功更新内容点赞数！");
                    return;
                }
            }
            System.out.println("内容id不存在，无法将缓存数据持久化！");
        }
    }

    private UserLikes userLikesDTOtoUserLikes(UserLikesDTO userLikesDTO){
        UserLikes userLikes = new UserLikes();

        userLikes.setStatus(userLikesDTO.getStatus());
        userLikes.setUserId(userLikesDTO.getLikeUserId());
        userLikes.setArticleId(userLikesDTO.getArticleId());
        userLikes.setGmtCreate(System.currentTimeMillis());
        userLikes.setGmtModified(userLikes.getGmtCreate());
        return userLikes;
    }

}
