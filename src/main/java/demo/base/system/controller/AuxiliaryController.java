package demo.base.system.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.service.AuxiliaryService;
import demo.common.controller.CommonController;

@Controller
public class AuxiliaryController extends CommonController {
	
	@Autowired
	private AuxiliaryService auxiliaryService;
	
	@GetMapping(value = { BaseUrl.ROBOT })
	public void robot(HttpServletRequest request, HttpServletResponse response) {
		auxiliaryService.robotHandle(request, response);
	}
	
	@GetMapping(value = { BaseUrl.ROBOTS })
	public void robots(HttpServletRequest request, HttpServletResponse response) {
		auxiliaryService.robotHandle(request, response);
	}
	
	@GetMapping(value = { BaseUrl.ROBOT_TXT })
	public void robotTxt(HttpServletRequest request, HttpServletResponse response) {
		auxiliaryService.robotHandle(request, response);
	}
	
	@GetMapping(value = { BaseUrl.ROBOTS_TXT })
	public void robotsTxt(HttpServletRequest request, HttpServletResponse response) {
		auxiliaryService.robotHandle(request, response);
	}
	
//	@GetMapping(value = { "/mp/MP_verify_rQHLtzQw6LZznCcT.txt" })
//	public void mpWeixinVerify(HttpServletRequest request, HttpServletResponse response) {
//		Resource resource = new ClassPathResource("/static_resources/txt/MP_verify_rQHLtzQw6LZznCcT.txt");
//		
//		String mimeType= "application/text/plain";
//		response.setContentType(mimeType);
//		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + resource.getFilename() +"\""));
//		try {
//			response.setContentLength((int)resource.contentLength());
//			InputStream is = resource.getInputStream();
//			FileCopyUtils.copy(is, response.getOutputStream());
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	@GetMapping(value = { BaseUrl.FAVICON })
	public void favicon(HttpServletResponse response) {
		Resource resource = new ClassPathResource("/static_resources/favicon.ico");
		
		String mimeType= "application/image/x-icon";
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + resource.getFilename() +"\""));
		try {
			response.setContentLength((int)resource.contentLength());
			InputStream is = resource.getInputStream();
			FileCopyUtils.copy(is, response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}