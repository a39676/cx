package demo.test.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.common.service.CryptoCoinConstantService;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@GetMapping(value = "/test")
	@ResponseBody
	public String test() {
		TestDTO t = new TestDTO();
		t.setDatetime(LocalDateTime.now());
		return t.toString();
	}

	@GetMapping(value = "/test2")
	@ResponseBody
	public String test2() {
		TestDTO t = new TestDTO();
		t.setDatetime(LocalDateTime.now());
		JSONObject j = JSONObject.fromObject(t);
		t = (TestDTO) JSONObject.toBean(j, TestDTO.class);
		return j.toString();
	}

	@GetMapping(value = "/t")
	@ResponseBody
	public String t() {
		return """
				{"testKey":"tv"}
				""";
	}

	@GetMapping(value = "/t2")
	@ResponseBody
	public String t2() {
		return "{\"k\":\"v\"}";
	}

	@Autowired
	protected CryptoCoinConstantService constantService;

	@GetMapping(value = "/t3")
	@ResponseBody
	public LocalDateTime t3() {
		constantService.setBinanceWebSocketLastActiveTime(LocalDateTime.now());
		return constantService.getBinanceWebSocketLastActiveTime();
	}

	@GetMapping(value = "/t4")
	@ResponseBody
	public String t4() {
		return "{\"k\":\"v\"}";
	}
	
}
