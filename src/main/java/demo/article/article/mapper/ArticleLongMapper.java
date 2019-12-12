package demo.article.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.article.article.pojo.dto.FindArticleLongByConditionDTO;
import demo.article.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.article.pojo.param.mapperParam.FindArticleIdsParam;
import demo.article.article.pojo.param.mapperParam.FindArticleLongParam;
import demo.article.article.pojo.param.mapperParam.UpdateArticleLongReviewStatuParam;
import demo.article.article.pojo.po.ArticleLong;
import demo.article.article.pojo.vo.ArticleLongVO;

public interface ArticleLongMapper {
    int insert(ArticleLong record);

    int insertSelective(ArticleLong record);
    
    List<Long> findArticleIds(FindArticleIdsParam param);
    
    ArticleLong findArticleLong(FindArticleLongParam param);

    ArticleLongVO findArticleLongByDecryptId(FindArticleLongByConditionDTO dto);
    
    int updateArticleLongReviewStatu(UpdateArticleLongReviewStatuParam param);
    
    Integer findArticleChannelIdByArticleId(Long articleId);

    int iWroteThis(@Param("userId")Long userId, @Param("articleId")Long articleId);
    
    int changeChannel(ChangeChannelParam param);
    
    List<ArticleLong> findArticleLongList(List<Long> articleIdList);
}