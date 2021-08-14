package demo.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	
	@GetMapping(value ="/test")
	@ResponseBody
	public String test() {
		TestDTO dto = new TestDTO();
		String g = new Gson().toJson(dto);
//		JSONObject j = JSONObject.fromObject(dto);
		return g;
//		return new TestDTO();
	}
	
	@PostMapping(value ="/test2")
	@ResponseBody
	public String test2(@RequestBody TestDTO dto) {
		System.out.println(dto);
		return "done";
	}
	
}
