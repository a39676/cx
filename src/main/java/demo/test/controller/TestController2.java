package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
		WechatRecordingUserFromParameterizedQrCodeDTO dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5jz2aWfZwMox6Q5kzK5RzVs");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5hsW7vvFhaFRldrgn9gX4PU");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5hd83T8QAw_PRQ3vu-gLmq4");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5msVP-e4c3RIc-TrG2MTXCk");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5iDDKAIllpC-JGBNCR1nRco");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5haTy99oay3-ZQczGlFOrXE");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5jyPN581kOaH6HITqUyt5qs");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5p6i2qeyOBbJIb8Ub8a_ork");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5qxcC9wQgGWv3Fco1QnsMDA");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5u8c4zdm-AMLVO8IbCKgfNQ");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5vXbdDf7quGLWaIgDCgSmec");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5g9kRg2NWqSt175dtzWPcCQ");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5mi-ZTlwB_kh2JvS8UOcdWA");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5kcZ4EHlJw4AISjY3zChHfU");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5l7x85vMLfNEyxvkHuGMpjI");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5srZ3qW0vplLYtlt1xNdsfU");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5nlKlrD2BtWq1Cvifu33-kA");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5sSUkuW7BASGtHGIRbDHVpQ");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5js6j5XWCg3ONJDBCLFI_w0");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5iIxLNCowCIrBGncNw_cwq8");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5l_w5Fjl7IyaQ1SF7fTjS1g");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5mAZJ1fOg8SVL3AqzTr2jFo");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5mPENpxtq38JtDTivAL1-Dw");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5qihIS3r9wIiTTiYIxhIhTw");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5rjoyCM6r8jRnCmD0GSNSDI");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5iRTo1HE3GlZCFuquTvAT0A");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId("oXt5d5lDP3vBwnWbE0jqBmBj0ERU");
		wechatUserService.recordingWechatUserFromParameterizedQrCode(dto);
		return "Done";
	}
}
