package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.dto.UserLikeCountDTO;
import cn.yvenxx.zhima_community.dto.UserLikesDTO;

import java.util.List;

public interface LikeRedisService {
    /**
     * 获取点赞状态
     * @param articleId
     * @param likeUserId
     * @return
     */
    Integer getLikeStatus(String articleId,String likeUserId);

    /**
     * 点赞状态变为1
     * @param articleId
     * @param likeUserId
     */
    void saveLikedToRedis(String articleId,String likeUserId);

    /**
     * 取消点赞 状态变为0
     * @param articleId
     * @param likeUserId
     */
    void unlikeFromRedis(String articleId,String likeUserId);

    /**
     * 删除一条点赞数量
     * @param articleId
     * @param likeUserId
     */
    void deleteLikedFromRedis(String articleId,String likeUserId);

    /**
     * 点赞数量 变化num
     * @param articleId
     * @param num
     */
    void decrementLikedCount(String articleId,Integer num);

    /**
     * 获取redis中存储的所有点赞数量
     * @return
     */
    List<UserLikeCountDTO> getLikedCountFromRedis();

    List<UserLikesDTO> getLikedDataFromRedis();
}
