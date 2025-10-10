package demo.tool.taobao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.dto.AddTaobaoProductSourceDTO;
import demo.tool.taobao.pojo.po.TaobaoProductSource;
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
	public CommonResult add(@RequestBody AddTaobaoProductSourceDTO dto) {
		return service.insert(dto);
	}

	@PostMapping(value = "/search")
	@ResponseBody
	public List<TaobaoProductSource> search(@RequestBody AddTaobaoProductSourceDTO dto) {
		return service.search(dto);
	}
}
