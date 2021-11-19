package demo.article.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.article.article.pojo.bo.ArticleLongSummaryBO;
import demo.article.article.pojo.dto.FindArticleLongSummaryListMapperDTO;
import demo.article.article.pojo.param.mapperParam.FindArticleHotSummaryListMapperParam;
import demo.article.article.pojo.po.ArticleLongSummary;
import demo.article.article.pojo.po.ArticleLongSummaryExample;

public interface ArticleLongSummaryMapper {
    long countByExample(ArticleLongSummaryExample example);

    int deleteByExample(ArticleLongSummaryExample example);

    int deleteByPrimaryKey(Long articleId);

    int insert(ArticleLongSummary record);

    int insertSelective(ArticleLongSummary record);

    List<ArticleLongSummary> selectByExampleWithRowbounds(ArticleLongSummaryExample example, RowBounds rowBounds);

    List<ArticleLongSummary> selectByExample(ArticleLongSummaryExample example);

    ArticleLongSummary selectByPrimaryKey(Long articleId);

    int updateByExampleSelective(@Param("record") ArticleLongSummary record, @Param("example") ArticleLongSummaryExample example);

    int updateByExample(@Param("record") ArticleLongSummary record, @Param("example") ArticleLongSummaryExample example);

    int updateByPrimaryKeySelective(ArticleLongSummary record);

    int updateByPrimaryKey(ArticleLongSummary record);
    
    ArticleLongSummaryBO findArticleLongSummary(Long articleId);
    
    List<ArticleLongSummaryBO> findArticleHotSummaryList(FindArticleHotSummaryListMapperParam param);
    
    List<ArticleLongSummaryBO> findArticleLongSummaryList(FindArticleLongSummaryListMapperDTO param);
}