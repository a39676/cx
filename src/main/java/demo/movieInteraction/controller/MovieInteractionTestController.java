package demo.movieInteraction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import httpHandel.HttpUtil;
import movie.pojo.constant.MovieInteractionUrl;

@Controller
@RequestMapping(value = "/mt", produces = "text/plain;charset=UTF-8")
public class MovieInteractionTestController {

	@Autowired
	private HttpUtil httpUtil;
	
	@GetMapping(value = "/test")
	@ResponseBody
	public String test() {
		try {
			String url = "http://127.0.0.1:10002" + MovieInteractionUrl.root + MovieInteractionUrl.simpleList;
			String response = String.valueOf(httpUtil.sendGet(url));
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
