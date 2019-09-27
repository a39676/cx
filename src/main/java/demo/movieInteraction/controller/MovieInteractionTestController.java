package demo.movieInteraction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import httpHandel.HttpUtil;
import movie.pojo.constant.MovieInteractionUrl;
import movie.pojo.dto.FindMovieSummaryListDTO;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/mt", produces = "text/plain;charset=UTF-8")
public class MovieInteractionTestController {

	@Autowired
	private HttpUtil httpUtil;
	
	@PostMapping(value = "/test")
	@ResponseBody
	public String test(@RequestBody FindMovieSummaryListDTO dto) {
		try {
			dto.setPageParam();
			JSONObject j = JSONObject.fromObject(dto);
	        
			String url = "http://127.0.0.1:10002" + MovieInteractionUrl.root + MovieInteractionUrl.simpleList;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
