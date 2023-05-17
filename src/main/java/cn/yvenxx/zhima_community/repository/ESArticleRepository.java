package cn.yvenxx.zhima_community.repository;

import cn.yvenxx.zhima_community.model.ESArticle;
import com.github.pagehelper.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
public interface ESArticleRepository extends ElasticsearchRepository<ESArticle,Long> {
    Page<ESArticle> queryESArticleByTitle(String title);

    
}
