package demo.article.article.service;


import org.springframework.web.servlet.ModelAndView;

import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.article.article.pojo.dto.ReadBurningMessageByPwdDTO;
import demo.article.article.pojo.result.ArticleBurnResult;
import demo.article.article.pojo.result.CreatingBurnMessageResult;
import jakarta.servlet.http.HttpServletRequest;

public interface ArticleBurnService {
	
	ModelAndView articleBurnLink(HttpServletRequest request);

	CreatingBurnMessageResult creatingBurnMessage(CreatingBurnMessageDTO dto, HttpServletRequest request);

	void burnArticleByBurnKey(String burnKey);

	ModelAndView readBurningMessage(String readKey);

	/** 清理时间过长的阅后即焚信息. */
	void cleanExpiredArticleBurn();

	ArticleBurnResult getBurningMessageByReadKeyAndPwd(ReadBurningMessageByPwdDTO dto);
}
