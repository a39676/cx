package demo.interaction.autoTest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import autoTest.jsonReport.pojo.constant.JsonReportInteractionUrl;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import auxiliaryCommon.pojo.constant.ServerHost;
import demo.baseCommon.controller.CommonController;
import demo.interaction.autoTest.pojo.constant.AutoTestUrl;
import demo.interaction.autoTest.service.AutoTestDemoPageService;
import httpHandel.HttpUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = AutoTestUrl.root)
public class AutoTestDemoController extends CommonController {

	@Autowired
	private AutoTestDemoPageService pageService;
	@Autowired
	private HttpUtil httpUtil;
	
	@PostMapping(value = AutoTestUrl.linkToATHome)
	public ModelAndView linkToATHome(HttpServletRequest request) {
		return pageService.linkToATHome(request);
	}
	
	@PostMapping(value = JsonReportInteractionUrl.findReportsByCondition)
	@ResponseBody
	public String findReportsByCondition(@RequestBody FindTestEventPageByConditionDTO dto) {
		try {
			JSONObject j = JSONObject.fromObject(dto);
	        
			String url = ServerHost.host2 + JsonReportInteractionUrl.root + JsonReportInteractionUrl.findReportsByCondition;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping(value = JsonReportInteractionUrl.findReportByTestEventId)
	@ResponseBody
	public String findReportByTestEventId(@RequestBody FindReportByTestEventIdDTO dto) {
		try {
			JSONObject j = JSONObject.fromObject(dto);
	        
			String url = ServerHost.host2 + JsonReportInteractionUrl.root + JsonReportInteractionUrl.findReportByTestEventId;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
