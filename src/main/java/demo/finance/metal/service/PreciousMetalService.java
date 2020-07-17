package demo.finance.metal.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.finance.metal.pojo.dto.InsertNewMetalPriceNoticeSettingDTO;
import precious_metal.pojo.dto.PreciousMetailPriceDTO;

public interface PreciousMetalService {

	CommonResultCX insertNewMetalPriceNoticeSetting(InsertNewMetalPriceNoticeSettingDTO dto);

	ModelAndView insertNewMetalPriceNoticeSetting();

	void noticeHandler();

	CommonResult reciveMetalPrice(PreciousMetailPriceDTO dto);

}
