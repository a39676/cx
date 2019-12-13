package demo.article.article.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleAdminService {

	CommonResultCX batchUpdatePrivateKey(BatchUpdatePrimaryKeyParam param);

	/**
	 * 复核文章前置接口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	CommonResultCX handelReviewArticle(ReviewArticleLongParam param) throws Exception;
	
	/**
	 * 可删除自己的文章
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	CommonResult deleteArticle(String privateKey) throws Exception;

	CommonResultCX changeChannel(ChangeChannelParam param) throws Exception;

	CommonResultCX setArticleHot(SetArticleHotParam controllerParam);

	/** 管理员专用, 如将文章转频道, 所以返回所有的频道(包括已逻辑删除频道) */
	List<ArticleChannelVO> findArticleChannel();

}
