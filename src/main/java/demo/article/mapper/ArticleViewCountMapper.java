package demo.article.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.article.pojo.po.ArticleViewCount;
import demo.article.pojo.po.example.ArticleViewCountExample;

public interface ArticleViewCountMapper {
    long countByExample(ArticleViewCountExample example);

    int deleteByExample(ArticleViewCountExample example);

    int insert(ArticleViewCount record);

    int insertSelective(ArticleViewCount record);

    List<ArticleViewCount> selectByExample(ArticleViewCountExample example);

    int updateByExampleSelective(@Param("record") ArticleViewCount record, @Param("example") ArticleViewCountExample example);

    int updateByExample(@Param("record") ArticleViewCount record, @Param("example") ArticleViewCountExample example);
    
    List<ArticleViewCount> findByArticleId(List<Long> articleIdList);
    
    int insertOrUpdateViewCount(Long articleId);
}