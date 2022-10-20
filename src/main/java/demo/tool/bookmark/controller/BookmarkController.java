package demo.tool.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.tool.bookmark.po.constant.BookmarkUrl;
import demo.tool.bookmark.pojo.dto.GetBookmarkWithPwdDTO;
import demo.tool.bookmark.pojo.result.GetBookmarkResult;
import demo.tool.bookmark.service.BookmarkService;

@Controller
@RequestMapping(value = BookmarkUrl.ROOT)
public class BookmarkController extends CommonController {

	@Autowired
	private BookmarkService service;
	
	@GetMapping(value = BookmarkUrl.MANAGER)
	public ModelAndView bookmarkManager() {
		return service.findAllBookmark();
	}
	
	@GetMapping(value = BookmarkUrl.DETAIL)
	public ModelAndView bookmarkDetail(@RequestParam String pk) {
		return service.getBookmark(pk);
	}
	
	@PostMapping(value = BookmarkUrl.DETAIL)
	@ResponseBody
	public GetBookmarkResult bookmarkDetail(@RequestBody GetBookmarkWithPwdDTO dto) {
		return service.getBookmark(dto);
	}
}
