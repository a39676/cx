package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.image.service.ImageService;
import demo.interaction.wechat.service.impl.WechatCommonService;
import demo.interaction.wechat.service.impl.WechatUserServiceImpl;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import demo.test.service.TestService2;
import wechatSdk.pojo.dto.WechatRecordingUserFromParameterizedQrCodeDTO;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends WechatCommonService {

	@Autowired
	private TestService2 testService2;

	@GetMapping(value = "/t1")
	public ModelAndView testView() {
		return testService2.testView();
	}

	@PostMapping(value = "/t2")
	@ResponseBody
	public String t2(@RequestBody TestDTO dto) {
		return "{\"k\":\"v\"}";
	}

	@GetMapping(value = "/t3")
	@ResponseBody
	public String t3() {
		return "Done";
	}

	@Autowired
	private ImageService imageService;

	@GetMapping(value = "/t4")
	@ResponseBody
	public String t4() {
		log.error("Call imageService.imageCleanAndDeleteFile();");
		imageService.imageCleanAndDeleteFile();
		return "Done";
	}

	@Autowired
	private WechatUserServiceImpl wechatUserService;

	@GetMapping(value = "/t5")
	@ResponseBody
	public String t5() {
		EncryptDTO eDto = null;
		WechatRecordingUserFromParameterizedQrCodeDTO dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setParameter("");
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setUserOpenId("oXt5d5l7x85vMLfNEyxvkHuGMpjI");
		eDto = encryptDTO(dto);
		EncryptDTO er = wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		CommonResult r = decryptEncryptDTO(er, CommonResult.class);
		log.error(r.getCode() + r.getMessage());
		
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setParameter("");
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setUserOpenId("oXt5d5srZ3qW0vplLYtlt1xNdsfU");
		eDto = encryptDTO(dto);
		wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		er = wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		r = decryptEncryptDTO(er, CommonResult.class);
		log.error(r.getCode() + r.getMessage());
		
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setParameter("");
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setUserOpenId("oXt5d5nlKlrD2BtWq1Cvifu33-kA");
		eDto = encryptDTO(dto);
		wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		er = wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		r = decryptEncryptDTO(er, CommonResult.class);
		log.error(r.getCode() + r.getMessage());
		
		
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setParameter("");
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setUserOpenId("oXt5d5sSUkuW7BASGtHGIRbDHVpQ");
		eDto = encryptDTO(dto);
		wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		er = wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		r = decryptEncryptDTO(er, CommonResult.class);
		log.error(r.getCode() + r.getMessage());
		
		
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setParameter("");
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setUserOpenId("oXt5d5js6j5XWCg3ONJDBCLFI_w0");
		eDto = encryptDTO(dto);
		wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		er = wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		r = decryptEncryptDTO(er, CommonResult.class);
		log.error(r.getCode() + r.getMessage());
		
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setParameter("");
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setUserOpenId("oXt5d5irvNz39BrBgFH-Z8X3X2HI");
		eDto = encryptDTO(dto);
		wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		er = wechatUserService.recordingWechatUserFromParameterizedQrCode(eDto);
		r = decryptEncryptDTO(er, CommonResult.class);
		log.error(r.getCode() + r.getMessage());
		
		return "Done";
	}

}
