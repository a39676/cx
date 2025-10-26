package demo.tool.taobao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceAddDTO;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceSearchDTO;
import demo.tool.taobao.service.TaobaoProductSourceService;

@Controller
@RequestMapping(value = "/taobaoProductSource")
public class TaobaoProductSourceController {

	@Autowired
	private TaobaoProductSourceService service;

	@GetMapping(value = "/")
	public ModelAndView taobaoProductSource() {
		return service.taobaoProductSource();
	}

	@PostMapping(value = "/add")
	@ResponseBody
	public CommonResult add(@RequestBody TaobaoProductSourceAddDTO dto) {
		return service.insert(dto);
	}

	@PostMapping(value = "/search")
	@ResponseBody
	public ModelAndView search(@RequestBody TaobaoProductSourceSearchDTO dto) {
		return service.search(dto);
	}
}
