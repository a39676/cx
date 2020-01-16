package demo.article.article.service;

import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.article.article.pojo.result.ArticleBurnResult;
import demo.article.article.pojo.result.CreatingBurnMessageResult;

public interface ArticleBurnService {

	CreatingBurnMessageResult creatingBurnMessage(CreatingBurnMessageDTO dto);

	ArticleBurnResult findArticleByReadKey(String readKey);

	void burnArticleByBurnKey(String burnKey);

}
