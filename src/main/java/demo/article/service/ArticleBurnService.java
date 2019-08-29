package demo.article.service;

import demo.article.pojo.result.ArticleBurnResult;
import demo.article.pojo.result.CreatingBurnMessageResult;
import net.sf.json.JSONObject;

public interface ArticleBurnService {

	CreatingBurnMessageResult creatingBurnMessage(Long userId, JSONObject jsonInput);

	ArticleBurnResult findArticleByReadKey(String readKey);

	void burnArticleByBurnKey(String burnKey);

}
