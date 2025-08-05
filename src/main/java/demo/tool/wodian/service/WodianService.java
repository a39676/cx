package demo.tool.wodian.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.wodian.pojo.dto.CreateWodianContractDTO;
import demo.tool.wodian.pojo.dto.WodianContractSerachConditionDTO;

public interface WodianService {

	CommonResult createContract(CreateWodianContractDTO dto);

	ModelAndView summaryView();

	ModelAndView getContractListViewByCondition(WodianContractSerachConditionDTO dto);

	ModelAndView getClientInfo(Long clientId);

}
