package demo.article.article.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.param.controllerParam.SetArticleHotParam;

public interface ArticleAdminService {

	/**
	 * 复核文章前置接口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	CommonResult handelReviewArticle(ReviewArticleLongParam param) throws Exception;
	
	/**
	 * 可删除自己的文章
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	CommonResult deleteArticle(String privateKey) throws Exception;

	CommonResult changeChannel(ChangeChannelParam param) throws Exception;

	CommonResult setArticleHot(SetArticleHotParam controllerParam);

	CommonResult deleteArticle(Long id) throws Exception;

}
