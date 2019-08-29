package demo.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.article.pojo.bo.ArticleUserChannelResultBO;
import demo.article.pojo.param.controllerParam.FindArticleUserDetailParam;
import demo.article.pojo.param.mapperParam.BatchUpdateFlashStatuParam;
import demo.article.pojo.param.mapperParam.FindArticleUserDetailByUserIdChannelIdParam;
import demo.article.pojo.param.mapperParam.InsertOrUpdateUserCoefficientParam;
import demo.article.pojo.param.mapperParam.InsertOrUpdateUserDailyPostLimitParam;
import demo.article.pojo.param.mapperParam.UpdateArticleUserCoefficientParam;
import demo.article.pojo.param.mapperParam.UpdateArticleUserDetailParam;
import demo.article.pojo.param.mapperParam.UpdateArticleUserPostLimitParam;
import demo.article.pojo.po.ArticleUserDetail;

public interface ArticleUserDetailMapper {
	int insert(ArticleUserDetail record);

    int insertSelective(ArticleUserDetail record);
    
    int insertOrUpdateUserCoefficient(InsertOrUpdateUserCoefficientParam param);
    
    int insertOrUpdateUserDailyPostLimit(InsertOrUpdateUserDailyPostLimitParam param);

    List<ArticleUserChannelResultBO> findArticleUserChannelResultBO(FindArticleUserDetailParam param);
    
    int batchUpdateFlashStatu(List<BatchUpdateFlashStatuParam> params);
    
    int updateArticleUserCoefficient(UpdateArticleUserCoefficientParam param);
    
    int batchUpdateArticleUserCoefficient(List<UpdateArticleUserCoefficientParam> params);
    
    Integer findArticleUserPostLimit(@Param("userId")Long userId, @Param("channelId")Long channelId);
    
    int updateArticleUserPostLimit(UpdateArticleUserPostLimitParam param);
    
    int updateArticleUserDetail(UpdateArticleUserDetailParam param);
    
    ArticleUserDetail findArticleUserDetailByUserIdChannelId(FindArticleUserDetailByUserIdChannelIdParam param);
    
    List<Integer> findChannelIdListByUserId(@Param("userId")Long userId);
}