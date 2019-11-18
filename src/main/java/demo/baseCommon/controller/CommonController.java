package demo.baseCommon.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import demo.tool.service.VisitDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public abstract class CommonController {
	
	@Autowired
	protected VisitDataService visitDataService;
	
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	protected JSONObject getJson(String data) {
		JSONObject json;
		try {
			json = JSONObject.fromObject(data);
		} catch (Exception e) {
			e.printStackTrace();
			json = JSONObject.fromObject("{\"data\":\"wrong data\"}");
		}
		return json;
	}
	
	protected void outputStr(HttpServletResponse response, String str) {
		try {
			response.getWriter().println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void outputJson(HttpServletResponse response, JSONObject json) {
		try {
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void outputJson(HttpServletResponse response, JSONArray jsonArray) {
		try {
			response.getWriter().println(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
