package demo.article.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.baseCommon.service.CommonService;
import demo.tool.service.TextFilter;

public class ArticleCommonService extends CommonService {
	
	@Autowired
	protected TextFilter textFilter;
	
	/*
	 * 2020-01-17
	 */
//	protected static String imageHttpUrlPattern;
//	private static final String[] imageSuffix = { "jpg", "jpeg", "bmp", "tiff", "png", "gif", "webp" };
//	static {{
//		String imageSuffixPattern = "";
//		for (int i = 0; i < imageSuffix.length; i++) {
//			imageSuffixPattern = imageSuffixPattern + "(" + imageSuffix[i] + ")";
//			if (i < imageSuffix.length - 1) {
//				imageSuffixPattern = imageSuffixPattern + "|";
//			}
//		}
//
//		imageHttpUrlPattern = "(https?://(?:.*)(/\\S+\\." + imageSuffixPattern + "))";
//	}}

}
