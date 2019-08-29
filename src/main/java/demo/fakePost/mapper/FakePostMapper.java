package demo.fakePost.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.article.pojo.po.ArticleEvaluationStore;
import demo.article.pojo.po.ArticleLong;
import demo.fakePost.pojo.param.mapperParam.FindArticleIdNotPassAndPostByShadowParam;
import demo.fakePost.pojo.param.mapperParam.ModifyArticleInfoParam;

public interface FakePostMapper {
	
	List<ArticleLong> findPassedArticleIdListByPeriod(@Param("startTime")Date startTime, @Param("endTime")Date endTime);

	int batchInserEvaluationStore(List<ArticleEvaluationStore> list);
	
	List<ArticleLong> findArticleIdNotPassAndPostByShadow(FindArticleIdNotPassAndPostByShadowParam param);
	
	int modifyArticleInfo(ModifyArticleInfoParam param);
	
	List<Long> getShadowUserId();
}
