package demo.article.article.service;

import org.springframework.web.servlet.ModelAndView;

import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.article.article.pojo.result.CreatingBurnMessageResult;

public interface ArticleBurnService {

	CreatingBurnMessageResult creatingBurnMessage(CreatingBurnMessageDTO dto);

	void burnArticleByBurnKey(String burnKey);

	ModelAndView readBurningMessage(String readKey);

}
