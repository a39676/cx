package demo.movieInteraction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import httpHandel.HttpUtil;
import movie.pojo.constant.MovieInteractionUrl;

@Controller
@RequestMapping(value = "/mt")
public class MovieInteractionTestController {

	@Autowired
	private HttpUtil httpUtil;
	
	@GetMapping(value = "/test")
	public void test() {
		try {
			String url = "http://127.0.0.1:10002" + MovieInteractionUrl.root + MovieInteractionUrl.simpleList;
//			HashMap<String, String> paramMap = new HashMap<String, String>();
//			FindMovieSummaryListDTO dto = new FindMovieSummaryListDTO();
//			dto.setMovieRegionType(movieRegionType);
			System.out.println(httpUtil.sendPost(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
