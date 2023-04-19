package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.model.UserLikes;
import com.github.pagehelper.Page;

public interface UserLikesService {

    UserLikes getLikeStatus(String articleId,String userId);

    /**
     * 保存点赞记录
     * @param userLike
     * @return
     */
    Boolean save(UserLikes userLike);
    /**
     * 更新点赞记录
     * @param userLike
     * @return
     */
    Boolean update(UserLikes userLike);
    /**
     * 根据内容的id查询点赞列表（即查询都谁给这个内容点赞过）
     * @param articleId 内容的id
     * @return
     */
    Page<UserLikes> getLikedListByArticleId(String articleId, int pageNum, int pageSize);

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给哪些内容点赞过）
     * @param likeUserId
     * @return
     */
    Page<UserLikes> getLikedListByLikeUserId(String likeUserId, int pageNum, int pageSize);

    /**
     * 通过被点赞内容和点赞人id查询是否存在点赞记录
     * @param articleId
     * @param likeUserId
     * @return
     */
    UserLikes getByArticleIdAndLikeUserId(String articleId, String likeUserId);

    /**
     * 将Redis里的点赞数据存入数据库中,True 表示还需要进一步持久化， False表示数据库中已存在该数据，无需进一步持久化
     */
    void transLikedFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();
}
