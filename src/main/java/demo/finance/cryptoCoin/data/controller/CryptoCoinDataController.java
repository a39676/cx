package demo.finance.cryptoCoin.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinTestUrl;
import demo.finance.cryptoCoin.data.pojo.dto.GetBigMoveSummaryDataDTO;
import demo.finance.cryptoCoin.data.pojo.result.GetBigMoveSummaryDataResult;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;

@Controller
@RequestMapping(value = CryptoCoinTestUrl.ROOT)
public class CryptoCoinDataController extends CommonController {

	@Autowired
	private CryptoCoinDataComplexService cryptoCoinDataComplexService;

	@GetMapping(value = CryptoCoinTestUrl.BIG_MOVE_VIEW)
	public ModelAndView getBigMoveSummaryView() {
		return cryptoCoinDataComplexService.getBigMoveSummaryView();
	}

	@PostMapping(value = CryptoCoinTestUrl.BIG_MOVE_DATA)
	@ResponseBody
	public GetBigMoveSummaryDataResult getBigMoveSummaryData(@RequestBody GetBigMoveSummaryDataDTO dto) {
		return cryptoCoinDataComplexService.getBigMoveSummaryData(dto);
	}

	@PostMapping(value = CryptoCoinTestUrl.BIG_MOVE_CHART)
	@ResponseBody
	public ModelAndView getBigMoveSummaryChart(@RequestBody BaseStrDTO dto) {
		return cryptoCoinDataComplexService.getBigMoveDataChart(Integer.valueOf(dto.getStr()));
	}

}
