package demo.article.service;

import java.util.List;

import demo.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.pojo.param.mapperParam.FindArticleChannelsParam;
import demo.article.pojo.vo.ArticleChannelVO;
import demo.baseCommon.pojo.result.CommonResult;

public interface ArticleAdminService {

	CommonResult batchUpdatePrivateKey(BatchUpdatePrimaryKeyParam param);

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

	List<ArticleChannelVO> findChannel(FindArticleChannelsParam param);

	CommonResult changeChannel(ChangeChannelParam param) throws Exception;

	CommonResult setArticleHot(SetArticleHotParam controllerParam);

}
