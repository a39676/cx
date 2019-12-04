package demo.article.article.service;

import demo.article.article.pojo.result.ArticleBurnResult;
import demo.article.article.pojo.result.CreatingBurnMessageResult;
import net.sf.json.JSONObject;

public interface ArticleBurnService {

	CreatingBurnMessageResult creatingBurnMessage(JSONObject jsonInput);

	ArticleBurnResult findArticleByReadKey(String readKey);

	void burnArticleByBurnKey(String burnKey);

}
