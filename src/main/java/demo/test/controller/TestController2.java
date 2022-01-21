package demo.test.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import demo.tool.other.service.QrCodeService;
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
		return "done";
	}
	
	@Autowired
	private QrCodeService qs;
	
	@GetMapping(value = "/t1")
	@ResponseBody
	public String t1(@RequestParam("u") String u) {
		try {
			return qs.decodeByImageUrl(u);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/t2")
	@ResponseBody
	public void t2(@RequestParam("c") String c, HttpServletResponse response) {
		qs.generator(response, c, null, null);
	}
}
