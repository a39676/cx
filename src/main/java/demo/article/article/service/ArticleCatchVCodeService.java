package demo.article.article.service;

import demo.article.article.pojo.po.ArticleSummaryVCode;
import demo.toyParts.vcode.pojo.po.VCode;

public interface ArticleCatchVCodeService {

	ArticleSummaryVCode findArticleSummaryInfo(VCode vcode);

}
