package demo.finance.metal.service;

import org.springframework.web.servlet.ModelAndView;

import demo.common.pojo.result.CommonResultCX;
import demo.finance.metal.pojo.dto.InsertNewMetalPriceNoticeSettingDTO;

public interface PreciousMetalNoticeService {

	void noticeHandler();

	CommonResultCX insertNewMetalPriceNoticeSetting(InsertNewMetalPriceNoticeSettingDTO dto);

	ModelAndView insertNewMetalPriceNoticeSetting();

}
