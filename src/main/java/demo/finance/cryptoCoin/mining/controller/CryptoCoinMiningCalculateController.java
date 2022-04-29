package demo.finance.cryptoCoin.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.mining.service.CryptoCoinMiningCalculateService;

@Controller
@RequestMapping(value = "/cryptoCoinMiningCalculate")
public class CryptoCoinMiningCalculateController {

	@Autowired
	private CryptoCoinMiningCalculateService service;
	
	@GetMapping(value = "/home")
	public ModelAndView home() {
		return service.home();
	}
}
