//package demo.toyParts.weixin.controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import demo.common.controller.CommonController;
//import demo.toyParts.weixin.pojo.constant.WXUrl;
//import demo.toyParts.weixin.service.WeixinService;
//
///**
// * --测试微信api接口 
// *
// */
//@Controller
//@RequestMapping(value = WXUrl.root)
//public class WeixinController extends CommonController {
//	
//	@Autowired
//	private WeixinService weixinService;
//
//	/**
//	 * 
//	 * @param signature 微信加密签名, signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
//	 * @param timestamp 时间戳
//	 * @param nonce 随机数
//	 * @param echostr 随机字符串
//	 * @param request 
//	 */
//	@GetMapping(value = WXUrl.tokenTest)
//	public void wxTest(
//			PrintWriter op, 
//			@RequestParam(value = "signature", defaultValue = "0") String signature, 
//			@RequestParam(value = "timestamp", defaultValue = "0") String timestamp, 
//			@RequestParam(value = "nonce", defaultValue = "0") String nonce, 
//			@RequestParam(value = "echostr", defaultValue = "0") String echostr, 
//			HttpServletRequest request
//			) {
//		op.print(weixinService.weixinTokenTest(signature, timestamp, nonce, echostr));
//	}
//	
////	@GetMapping(value = WXUrl.getNewToken)
//	public void getNewToken(HttpServletRequest request, PrintWriter op) throws Exception {
//		op.print(weixinService.getNewToken());
//	}
//	
//	@GetMapping(value = WXUrl.weixin)
//	public void getNewWXMessageTest(PrintWriter op, 
//			@RequestParam(value = "signature", defaultValue = "0") String signature, 
//			@RequestParam(value = "timestamp", defaultValue = "0") String timestamp, 
//			@RequestParam(value = "nonce", defaultValue = "0") String nonce, 
//			@RequestParam(value = "echostr", defaultValue = "0") String echostr, 
//			HttpServletRequest request) {
//		op.print(weixinService.weixinTokenTest(signature, timestamp, nonce, echostr));
//	}
//	
//	@PostMapping(value = WXUrl.weixin)
//	public void getNewWXMessage(HttpServletRequest request, HttpServletResponse response, PrintWriter op) throws IOException {
//		weixinService.getNewWXMessage(request, response, op);
//	}
//	
//}
