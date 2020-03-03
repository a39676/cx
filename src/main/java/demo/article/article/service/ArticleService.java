package demo.article.article.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.dto.ArticleFeedbackDTO;
import demo.article.article.pojo.dto.EditArticleLongDTO;
import demo.article.article.pojo.dto.ReadyToEditArticleLongDTO;
import demo.article.article.pojo.param.controllerParam.CreateArticleParam;
import demo.article.article.pojo.param.controllerParam.CreatingArticleParam;
import demo.article.article.pojo.param.controllerParam.FindArticleLongByArticleSummaryPrivateKeyDTO;
import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleService {

	/** 返回创建文章编辑页面 */
	ModelAndView buildCreatingArticleLongView(CreatingArticleParam controllerParam);

	/**
	 * 通过摘要的加密key 获取指定的文章
	 * 
	 * @param param
	 * @return
	 */
	FindArticleLongResult findArticleLongByArticleSummaryPrivateKey(
			FindArticleLongByArticleSummaryPrivateKeyDTO param, HttpServletRequest request);

	/**
	 * 以userId, 加密articleId验证是否对应作者
	 * 
	 * @param userId
	 * @param privateKey
	 * @return
	 */
	boolean iWroteThis(String privateKey);

	/**
	 * 定时任务逻辑, 拉取article_long_review中  缺失作者ID的数据, 补填
	 */
	void refillArticleLongReviewCreatorId();

	CommonResultCX articleLongFeedback(ArticleFeedbackDTO dto, HttpServletRequest request);

	/** 发布文章, 根据条件选择是普通单文章发布 or 批量发布 */
	CommonResult crateArticleLongPrefixServcie(CreateArticleParam cp)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, IOException;

	/** 编辑文章 */
	ModelAndView readyToEditArticleLong(ReadyToEditArticleLongDTO dto);

	/**
	 * 用于保存新建/编辑的文章,
	 * 在编辑情况下, creatorId 是编辑者ID, 不一定是原作者ID, 用于命名文件名
	 * @param storePrefixPath
	 * @param creatorId
	 * @param content
	 * @return
	 * @throws IOException
	 */
	ArticleFileSaveResult saveArticleFile(String storePrefixPath, Long creatorId, String content) throws IOException;

	CommonResultCX editArticleLongHandler(EditArticleLongDTO dto) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException;

	ModelAndView readArticleLong(String pk, HttpServletRequest request);

}
