package demo.article.article.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import demo.article.article.pojo.dto.FindArticleLongSummaryListDTO;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongSummaryListResultV3;

public interface ArticleSummaryService {

	int insertArticleLongSummary(Long userId, Long articleId, String title, String finalFilePath)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException;
	
	FindArticleLongSummaryListResultV3 summaryListByChannelIdV4(FindArticleLongSummaryListDTO param,
			HttpServletRequest request);

}
