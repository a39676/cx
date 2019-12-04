package demo.article.fakePost.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.article.article.pojo.po.ArticleEvaluationStore;
import demo.article.article.pojo.po.ArticleLong;
import demo.article.fakePost.pojo.param.mapperParam.FindArticleIdNotPassAndPostByShadowParam;
import demo.article.fakePost.pojo.param.mapperParam.ModifyArticleInfoParam;

public interface FakePostMapper {
	
	List<ArticleLong> findPassedArticleIdListByPeriod(@Param("startTime")Date startTime, @Param("endTime")Date endTime);

	int batchInserEvaluationStore(List<ArticleEvaluationStore> list);
	
	List<ArticleLong> findArticleIdNotPassAndPostByShadow(FindArticleIdNotPassAndPostByShadowParam param);
	
	int modifyArticleInfo(ModifyArticleInfoParam param);
	
	List<Long> getShadowUserId();
}
