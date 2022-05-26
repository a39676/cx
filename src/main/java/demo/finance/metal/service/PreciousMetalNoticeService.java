package demo.finance.metal.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.metal.pojo.dto.InsertNewMetalPriceNoticeSettingDTO;

public interface PreciousMetalNoticeService {

	void noticeHandler();

	CommonResult insertNewMetalPriceNoticeSetting(InsertNewMetalPriceNoticeSettingDTO dto);

	ModelAndView insertNewMetalPriceNoticeSetting();

}
