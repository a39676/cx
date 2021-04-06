package demo.finance.cryptoCoin.tool.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoCoinLocalDataFinderResult;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLocalDataTool;

@Controller
@RequestMapping(value = "/cryptoCoinLocal")
public class CryptoCoinDataLocalContoller extends CommonController {

	@Autowired
	private CryptoCoinLocalDataTool dataService;

	@GetMapping(value = "/finder1")
	@ResponseBody
	public CryptoCoinLocalDataFinderResult finder1() {
		LocalDateTime startDateTime = LocalDateTime.of(2021, 1, 1, 0, 0);
		LocalDateTime endDateTime = LocalDateTime.of(2021, 3, 28, 0, 0);
		Double upA = 200D;
		Double downA = 0.5;
		return dataService.finder1(startDateTime, endDateTime, upA , downA);
	}
	
}
