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

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.mining.pojo.dto.CryptoCoinShareCalculateDTO;
import demo.finance.cryptoCoin.mining.pojo.dto.CryptoCoinSharingCalculateDetailSearchDTO;
import demo.finance.cryptoCoin.mining.pojo.dto.DeleteSharingDetailDTO;
import demo.finance.cryptoCoin.mining.pojo.dto.UpdateAllocationAssistantDTO;
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
	public ModelAndView readSharingDetail(@RequestParam("pk") String pk) {
		return service.readSharingDetail(pk);
	}
	
	@PostMapping(value = "/deleteSharingDetail")
	@ResponseBody
	public CommonResult deleteSharingDetail(@RequestBody DeleteSharingDetailDTO dto) {
		return service.deleteSharingDetail(dto);
	}
	
	@GetMapping(value = "/sharingCalculateDetailListSearchView")
	public ModelAndView sharingCalculateDetailListSearchView() {
		return service.sharingCalculateDetailListSearchView();
	}
	
	@PostMapping(value = "/sharingCalculateDetailListSearch")
	public ModelAndView sharingCalculateDetailListSearch(@RequestBody CryptoCoinSharingCalculateDetailSearchDTO dto) {
		return service.sharingCalculateDetailListSearch(dto);
	}
	
	@PostMapping(value = "/updateAssistant")
	@ResponseBody
	public CommonResult updateAssistant(@RequestBody UpdateAllocationAssistantDTO dto) {
		return service.updateAssistant(dto);
	}
	
}
