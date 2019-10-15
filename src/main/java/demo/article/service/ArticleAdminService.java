package demo.article.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.pojo.param.mapperParam.FindArticleChannelsParam;
import demo.article.pojo.vo.ArticleChannelVO;
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

	List<ArticleChannelVO> findChannel(FindArticleChannelsParam param);

	CommonResultCX changeChannel(ChangeChannelParam param) throws Exception;

	CommonResultCX setArticleHot(SetArticleHotParam controllerParam);

}
