package demo.finance.cryptoCoin.sharing.controller;

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
import demo.finance.cryptoCoin.sharing.pojo.constant.CryptoCoinSharingUrl;
import demo.finance.cryptoCoin.sharing.pojo.dto.CryptoCoinShareCalculateDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.CryptoCoinSharingCalculateDetailSearchDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.DeleteSharingDetailDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.UpdateAllocationAssistantDTO;
import demo.finance.cryptoCoin.sharing.pojo.result.CryptoCoinShareCalculateResult;
import demo.finance.cryptoCoin.sharing.service.CryptoCoinSharingCalculateService;

@Controller
@RequestMapping(value = CryptoCoinSharingUrl.ROOT)
public class CryptoCoinSharingCalculateController {

	@Autowired
	private CryptoCoinSharingCalculateService service;

	@GetMapping(value = CryptoCoinSharingUrl.HOME)
	public ModelAndView home() {
		return service.home();
	}

	@PostMapping(value = CryptoCoinSharingUrl.SHARING_CALCULATE)
	@ResponseBody
	public CryptoCoinShareCalculateResult sharingCalculate(@RequestBody CryptoCoinShareCalculateDTO dto) {
		return service.getCalculateResult(dto);
	}

	@GetMapping(value = CryptoCoinSharingUrl.CALCULATE_DETAIL)
	public ModelAndView readSharingDetail(@RequestParam("pk") String pk) {
		return service.readSharingDetail(pk);
	}
	
	@PostMapping(value = CryptoCoinSharingUrl.DELETE_SHAREING_DETAIL)
	@ResponseBody
	public CommonResult deleteSharingDetail(@RequestBody DeleteSharingDetailDTO dto) {
		return service.deleteSharingDetail(dto);
	}
	
	@GetMapping(value = CryptoCoinSharingUrl.SHARING_CALCULATE_DETAIL_LIST_SEARCH_VIEW)
	public ModelAndView sharingCalculateDetailListSearchView() {
		return service.sharingCalculateDetailListSearchView();
	}
	
	@PostMapping(value = CryptoCoinSharingUrl.SHARING_CALCULATE_DETAIL_LIST_SEARCH)
	public ModelAndView sharingCalculateDetailListSearch(@RequestBody CryptoCoinSharingCalculateDetailSearchDTO dto) {
		return service.sharingCalculateDetailListSearch(dto);
	}
	
	@PostMapping(value = CryptoCoinSharingUrl.UPDATE_ASSISTANT)
	@ResponseBody
	public CommonResult updateAssistant(@RequestBody UpdateAllocationAssistantDTO dto) {
		return service.updateAssistant(dto);
	}
	
}
