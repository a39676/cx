package demo.tool.wodian.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.wodian.pojo.dto.CreateWodianContractDTO;
import demo.tool.wodian.pojo.dto.WodianContractSerachConditionDTO;
import demo.tool.wodian.pojo.result.WodianContractListResult;

public interface WodianService {

	WodianContractListResult getContractListByCondition(WodianContractSerachConditionDTO dto);

	CommonResult createContract(CreateWodianContractDTO dto);

	ModelAndView summaryView();

}
