package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

	@Autowired
	private SnowFlake snowFlake;
	@Autowired
	protected RedisTemplate<String, String> redisTemplate;

	@GetMapping(value = "/snowFlake")
	@ResponseBody
	public String snowFlake() {
		return String.valueOf(snowFlake.getNextId());
	}
	
}
