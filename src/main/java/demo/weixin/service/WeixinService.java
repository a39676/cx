package demo.weixin.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.weixin.pojo.result.WeixinCommonResult;

public interface WeixinService {

	String weixinTokenTest(String signature, String timestamp, String nonce, String echostr);

	WeixinCommonResult getNewToken() throws Exception;

	void getNewWXMessage(HttpServletRequest request, HttpServletResponse response, PrintWriter op) throws IOException;

}
