package demo.article.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import demo.article.pojo.param.controllerParam.FindArticleLongSummaryListControllerParam;
import demo.article.pojo.result.jsonRespon.FindArticleLongSummaryListResultV3;

public interface ArticleSummaryService {

	int insertArticleLongSummary(Long userId, Long articleId, String title, String finalFilePath)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException;
	
	
	/**
	 * 查找指定频道的--置顶--文章摘要列表,直接投放页面显示.
	 * 
	 * @param param
	 * @return
	 */
	FindArticleLongSummaryListResultV3 articleLongSummaryListByChannelIdV3(
			FindArticleLongSummaryListControllerParam controllerParam);
	/**
	 * 查找指定频道的文章摘要列表,直接投放页面显示.
	 * 
	 * @param param
	 * @return
	 */
	FindArticleLongSummaryListResultV3 articleLongSummaryHotListByChannelIdV3(
			FindArticleLongSummaryListControllerParam controllerParam);
}
