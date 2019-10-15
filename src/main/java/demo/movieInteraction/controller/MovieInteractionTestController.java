package demo.movieInteraction.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import httpHandel.HttpUtil;
import movie.pojo.constant.MovieInteractionUrl;
import movie.pojo.dto.FindMovieDetailDTO;
import movie.pojo.dto.FindMovieSummaryListDTO;
import movie.pojo.type.MovieRegionType;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/mt", produces = "text/plain;charset=UTF-8")
public class MovieInteractionTestController {

	@Autowired
	private HttpUtil httpUtil;
	
	@PostMapping(value = MovieInteractionUrl.simpleList)
	@ResponseBody
	public String simpleList(@RequestBody FindMovieSummaryListDTO dto) {
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
	
	@PostMapping(value = MovieInteractionUrl.movieDetail)
	@ResponseBody
	public String findMovieDetail(@RequestBody FindMovieDetailDTO dto) {
		try {
			JSONObject j = JSONObject.fromObject(dto);
	        
			String url = "http://127.0.0.1:10002" + MovieInteractionUrl.root + MovieInteractionUrl.movieDetail;
			String response = String.valueOf(httpUtil.sendPostRestful(url, j.toString()));
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping(value = "/movieRegionType")
	@ResponseBody
	public String movieRegionType() {
		Map<String, String> l = new HashMap<String, String>();
		for(MovieRegionType i : MovieRegionType.values()) {
			l.put(i.getCode().toString(), i.getName());
		}
		return l.toString();
	}
}
