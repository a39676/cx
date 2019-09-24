package demo.trading.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.account_holder.controller.AccountHolderController;
import demo.account_holder.pojo.po.AccountHolder;
import demo.baseCommon.pojo.param.controllerParam.InsertNewTransationParam;
import demo.trading.pojo.CommonTransationParties;
import demo.trading.pojo.constant.TradingUrl;
import demo.trading.pojo.constant.TradingViews;
import demo.trading.pojo.po.TradingRecorder;
import demo.trading.pojo.result.InsertTradingRecorderResult;
import demo.trading.service.TradingService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = TradingUrl.tradingRoot)
public class TradingController {
	
	@Autowired
	private AccountHolderController accountHolderController;
	
	@Autowired
	private TradingService tradingService;
	
	
	public InsertTradingRecorderResult insertTradingRecorderSelective(InsertNewTransationParam param, Long accountId){
		return tradingService.insertTradingRecorder(param, accountId);
	}
	
	public TradingRecorder getTradingRecorderById(Long tradingRecorderId) {
		return tradingService.getTradingRecordById(tradingRecorderId);
	}
	
	@GetMapping(value = TradingUrl.insertHolderCommonTransation)
	public ModelAndView insertHolderCommonTransation() {
		ModelAndView view = new ModelAndView(TradingViews.insertHolderCommonTransation);
		return view;
	}
	
	@PostMapping(value = TradingUrl.getHolderCommonTransation)
	public List<CommonTransationParties> getCurrentCommonTransation() {
		List<AccountHolder> holderList = accountHolderController.getCurrentHolders();
		AccountHolder holder = null;
		if(holderList != null && !holderList.isEmpty()) {
			holder = holderList.get(0);
			return tradingService.getCurrentCommonTransation(holder.getAccountHolderId(), null);
		} else {
			return new ArrayList<CommonTransationParties>();
		}
	}
	
	@GetMapping(value = TradingUrl.importTradingRecordFromFiles)
	public ModelAndView importTradingRecordFromFiles() {
		ModelAndView view = new ModelAndView(TradingViews.importTradingRecordFromFiles);
		return view;
	}
	
	@PostMapping(value = TradingUrl.importTradingRecordFromFiles)
	public void importTradingRecordFromFiles(
			HttpServletResponse response, 
			String tradingRecordTxtPath
			) throws IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String result = tradingService.importTradingRecordFromFiles(tradingRecordTxtPath);
		json.put("data", result);
		out.print(json);
		return;
	}
	
	
}