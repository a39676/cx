package demo.article.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.pojo.param.controllerParam.ArticleLongComplaintParam;
import demo.article.pojo.param.controllerParam.CreateArticleParam;
import demo.article.pojo.param.controllerParam.CreatingArticleParam;
import demo.article.pojo.param.controllerParam.FindArticleLongByArticleSummaryPrivateKeyParam;
import demo.article.pojo.param.controllerParam.LikeHateThisChannelParam;
import demo.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.pojo.result.jsonRespon.FindArticleLongResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleService {

	String getImageHttpUrlPattern();

	/** 返回创建文章编辑页面 */
	ModelAndView creatingArticleLong(CreatingArticleParam controllerParam);

	/**
	 * 通过摘要的加密key 获取指定的文章
	 * 
	 * @param param
	 * @return
	 */
	FindArticleLongResult findArticleLongByArticleSummaryPrivateKey(
			FindArticleLongByArticleSummaryPrivateKeyParam param);

	/**
	 * 以userId, 加密articleId验证是否对应作者
	 * 
	 * @param userId
	 * @param privateKey
	 * @return
	 */
	boolean iWroteThis(Long userId, String privateKey);

	CommonResultCX likeOrHateThisChannel(LikeHateThisChannelParam inputParam);

	ArticleFileSaveResult saveArticleFile(String storePrefixPath, Long userId, String content) throws IOException;

	/**
	 * 定时任务逻辑, 拉取article_long_review中  缺失作者ID的数据, 补填
	 */
	void refillArticleLongReviewCreatorId();

	CommonResultCX articleLongComplaint(ArticleLongComplaintParam controllerParam);

	Long decryptPrivateKey(String pk);

	String encryptId(Long id);

	/** 发布文章, 根据条件选择是普通单文章发布 or 批量发布 */
	CommonResult crateArticleLongPrefixServcie(CreateArticleParam cp)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, IOException;

}
