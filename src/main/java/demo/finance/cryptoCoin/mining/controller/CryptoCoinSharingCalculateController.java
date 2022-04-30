package demo.finance.cryptoCoin.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.mining.pojo.dto.CryptoCoinShareCalculateDTO;
import demo.finance.cryptoCoin.mining.pojo.result.CryptoCoinShareCalculateResult;
import demo.finance.cryptoCoin.mining.service.CryptoCoinSharingCalculateService;

@Controller
@RequestMapping(value = "/cryptoCoinSharingCalculate")
public class CryptoCoinSharingCalculateController {

	@Autowired
	private CryptoCoinSharingCalculateService service;

	@GetMapping(value = "/home")
	public ModelAndView home() {
		return service.home();
	}

	@PostMapping(value = "/sharingCalculate")
	@ResponseBody
	public CryptoCoinShareCalculateResult sharingCalculate(@RequestBody CryptoCoinShareCalculateDTO dto) {
		return service.getCalculateResult(dto);
	}

	@GetMapping(value = "/calculateDetail")
	public ModelAndView readSharingDetail(@RequestParam("id") Long id) {
		return service.readSharingDetail(id);
	}
}
