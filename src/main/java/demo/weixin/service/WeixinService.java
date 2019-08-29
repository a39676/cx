package demo.weixin.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.baseCommon.pojo.result.CommonResult;

public interface WeixinService {

	String weixinTokenTest(String signature, String timestamp, String nonce, String echostr);

	CommonResult getNewToken() throws Exception;

	void getNewWXMessage(HttpServletRequest request, HttpServletResponse response, PrintWriter op) throws IOException;

}
