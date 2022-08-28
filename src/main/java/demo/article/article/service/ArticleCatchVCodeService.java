package demo.article.article.service;

import demo.article.article.pojo.po.ArticleSummaryVCode;
import demo.toyParts.vcode.pojo.po.Vcode;

public interface ArticleCatchVCodeService {

	ArticleSummaryVCode findArticleSummaryInfo(Vcode vcode);

}
