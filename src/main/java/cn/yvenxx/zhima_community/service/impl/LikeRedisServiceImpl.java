package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.dto.UserLikeCountDTO;
import cn.yvenxx.zhima_community.dto.UserLikesDTO;
import cn.yvenxx.zhima_community.service.LikeRedisService;
import cn.yvenxx.zhima_community.utils.DateUtil;
import cn.yvenxx.zhima_community.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LikeRedisServiceImpl implements LikeRedisService {
    /**
     *  status 1是点赞了  0 是未点赞
     *  返回 -1是不存在
     */
    @Autowired
    private HashOperations<String, String, Object> redisHash;// Redis Hash

    @Override
    public Integer getLikeStatus(String articleId, String likeUserId) {
        if (redisHash.hasKey(RedisKeyUtils.MAP_KEY_USER_LIKED,RedisKeyUtils.getLikedKey(articleId,likeUserId))){
            HashMap<String,Object> map = (HashMap<String, Object>) redisHash.get(RedisKeyUtils.MAP_KEY_USER_LIKED,RedisKeyUtils.getLikedKey(articleId,likeUserId));
            return (Integer) map.get("status");
        }
        return -1;
    }

    @Override
    public void saveLikedToRedis(String articleId, String likeUserId) {
        String key = RedisKeyUtils.getLikedKey(articleId, likeUserId);
        // 封装value 喜欢状态 更新时间
        HashMap<String,Object> map = new HashMap<>();
        //点赞状态置为1
        map.put("status",1);
        map.put("updateTime", System.currentTimeMillis());

        redisHash.put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, map);
    }

    @Override
    public void unlikeFromRedis(String articleId, String likeUserId) {
        // 生成key
        String key = RedisKeyUtils.getLikedKey(articleId, likeUserId);
        // 封装value 喜欢状态 更新时间
        HashMap<String,Object> map = new HashMap<>();
        map.put("status",0);
        map.put("updateTime", System.currentTimeMillis());// 存入当前时间戳

        redisHash.put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, map);
    }

    @Override
    public void deleteLikedFromRedis(String articleId, String likeUserId) {
        String key = RedisKeyUtils.getLikedKey(articleId, likeUserId);
        redisHash.delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    @Override
    public void decrementLikedCount(String articleId, Integer num) {
        redisHash.increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, articleId, num);
    }

    @Override
    public List<UserLikeCountDTO> getLikedCountFromRedis() {
        // scan 读取数据，比key匹配优雅
        Cursor<Map.Entry<String, Object>> cursor = redisHash.scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<UserLikeCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<String, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            UserLikeCountDTO dto = new UserLikeCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisHash.delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return list;
    }

    @Override
    public List<UserLikesDTO> getLikedDataFromRedis() {
        // scan 读取数据，比key匹配优雅
        Cursor<Map.Entry<String, Object>> cursor = redisHash.scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);

        List<UserLikesDTO> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<String, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 infoId，likedPostId, 解析value
            String[] split = key.split("::");
            String infoId = split[0];
            String likeUserId = split[1];
            HashMap<String, Object> map = (HashMap<String, Object>) entry.getValue();
            Integer status = (Integer) map.get("status");
            long updateTimeStamp = (long) map.get("updateTime");
            LocalDateTime updateTime = DateUtil.getDateTimeOfTimestamp(updateTimeStamp);// 时间戳转为LocalDateTime

            //组装成 UserLike 对象
            UserLikesDTO userLikesDTO = new UserLikesDTO(infoId, likeUserId, status, updateTime);
            list.add(userLikesDTO);

            //存到 list 后从 Redis 中清理缓存
            redisHash.delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }
        return list;
    }
}
