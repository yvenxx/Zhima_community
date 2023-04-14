package cn.yvenxx.zhima_community.utils;

public class RedisKeyUtils {
    /**
     * 保存用户点赞内容 数据 的key
     */
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    /**
     * 保存内容被点赞 数量 的key
     */
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

    /**
     * 拼接被点赞的内容id和点赞的人的id作为key。格式 222222::333333
     * @param articleId
     * @param likeUserId
     * @return
     */
    public static String getLikedKey(String articleId, String likeUserId){
        return articleId +
                "::" +
                likeUserId;
    }
}