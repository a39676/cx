package demo.article.article.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.article.article.pojo.result.CreatingBurnMessageResult;

public interface ArticleBurnService {
	
	ModelAndView articleBurnLink(HttpServletRequest request);

	CreatingBurnMessageResult creatingBurnMessage(CreatingBurnMessageDTO dto, HttpServletRequest request);

	void burnArticleByBurnKey(String burnKey);

	ModelAndView readBurningMessage(String readKey);

	/** 清理时间过长的阅后即焚信息. */
	void cleanExpiredArticleBurn();
}
