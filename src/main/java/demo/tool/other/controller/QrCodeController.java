package demo.tool.other.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.tool.other.pojo.constant.ToolUrlConstant;
import demo.tool.other.pojo.dto.QrCodeDTO;
import demo.tool.other.service.QrCodeService;

@Controller
@RequestMapping(value = ToolUrlConstant.root + "/qrcode")
public class QrCodeController extends CommonController {

	@Autowired
	private QrCodeService service;
	
	@GetMapping(value = "/")
	public ModelAndView view() {
		return new ModelAndView("toolJSP/QrCode/QrCode");
	}
	
	@PostMapping(value = "/decode")
	@ResponseBody
	public List<String> decode(@RequestBody QrCodeDTO dto) {
		return service.decodeFromContent(dto.getContent());
	}
	
	@PostMapping(value = "/generator")
	@ResponseBody
	public String generator(@RequestBody QrCodeDTO dto, HttpServletResponse response) {
		return service.generatorToBase64(response, dto.getContent(), null, null);
	}
	
	
}
