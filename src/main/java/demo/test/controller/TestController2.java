package demo.test.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.common.service.CryptoCoinConstantService;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import demo.toyParts.educate.pojo.result.ExerciesBuildResult;
import demo.toyParts.educate.service.ExerciesServiceMathG1_1;
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
	
	@Autowired
	private ExerciesServiceMathG1_1 es;
	
	@GetMapping(value = "/t5")
	@ResponseBody
	public ExerciesBuildResult t5() {
		ExerciesBuildResult r = es.buildExercies();
		return r;
	}
	
	@GetMapping(value = "/t6")
	@ResponseBody
	public ModelAndView t6() {
		return es.getExercies();
	}
	
	@PostMapping(value = "/t7")
	@ResponseBody
	public CommonResult t7(@RequestBody CreatingBurnMessageDTO dto) {
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		
		System.out.println(dto);
		return r;
	}
}
