package demo.article.service;

import demo.article.pojo.po.ArticleSummaryVCode;
import demo.vcode.pojo.po.VCode;

public interface ArticleCatchVCodeService {

	ArticleSummaryVCode findArticleSummaryInfo(VCode vcode);

}
