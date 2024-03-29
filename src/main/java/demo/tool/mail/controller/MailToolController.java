package demo.tool.mail.controller;
/**
 *  2021-07-12
 *  准备废弃 email 模块, 以Telegram 代替
 * 
 */

//package demo.tool.controller;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import auxiliaryCommon.pojo.result.CommonResult;
//import demo.base.system.pojo.bo.SystemConstantStore;
//import demo.base.system.service.impl.SystemConstantService;
//import demo.common.controller.CommonController;
//import demo.tool.pojo.constant.ToolUrlConstant;
//import demo.tool.pojo.dto.SetMailBaseParam;
//
//@Controller
//@RequestMapping(value = ToolUrlConstant.root + ToolUrlConstant.mail)
//public class MailToolController extends CommonController {
//
//	// @Autowired
//	// private MailService mailService;
//	@Autowired
//	private SystemConstantService systemConstantService;
//
//	/**
//	 * 请留意,本方法只刷新内存中常量,下次服务器重启时会重新从数据库获取
//	 * @param data
//	 * @param response
//	 * @return 
//	 */
//	@PostMapping(value = ToolUrlConstant.setMailBase)
//	@ResponseBody
//	public CommonResult setMailBase(@RequestBody SetMailBaseParam dto, HttpServletResponse response) {
//		CommonResult r = new CommonResult();
//		if(StringUtils.isNotBlank(dto.getMailName()) && StringUtils.isNotBlank(dto.getMailPasswod())) {
//			systemConstantService.setValByName(SystemConstantStore.adminMailName, dto.getMailName());
//			systemConstantService.setValByName(SystemConstantStore.adminMailPwd, dto.getMailPasswod());
//			r.setIsSuccess();
//		}
//
//		return r;
//	}
//
//	
//}
