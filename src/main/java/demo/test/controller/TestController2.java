package demo.test.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.config.costom_component.SnowFlake;
import demo.test.pojo.constant.TestUrl;

@Controller
@RequestMapping(value = { TestUrl.testRoot2 })
public class TestController2 extends CommonController {

//	@Autowired
//	private TestService testService;

	@Autowired
	private SnowFlake snowFlake;

	@GetMapping(value = "/snowFlake")
	@ResponseBody
	public String snowFlake() {
		return String.valueOf(snowFlake.getNextId());
	}

	@GetMapping(value = "/redisTest")
	@ResponseBody
	public Set<String> redisTest() {
		redisTemplate.opsForSet().add("s1", "i1");
		redisTemplate.opsForSet().add("s1", "i2");
		redisTemplate.opsForSet().add("s1", "i1");
		redisTemplate.opsForSet().add("s1", "i3");
		
		return redisTemplate.opsForSet().members("s1");
	}
}
