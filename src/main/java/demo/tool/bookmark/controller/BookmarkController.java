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

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.tool.bookmark.po.constant.BookmarkUrl;
import demo.tool.bookmark.pojo.dto.BookmarkWeightChangeMainDTO;
import demo.tool.bookmark.pojo.dto.CreateBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.DeleteBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.DeleteBookmarkUrlDTO;
import demo.tool.bookmark.pojo.dto.EditBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.EditBookmarkUrlDTO;
import demo.tool.bookmark.pojo.dto.GetBookmarkWithPwdDTO;
import demo.tool.bookmark.pojo.dto.RemoveEmptyTagDTO;
import demo.tool.bookmark.pojo.dto.UploadBookmarkDTO;
import demo.tool.bookmark.pojo.result.CreateBookmarkTagResult;
import demo.tool.bookmark.pojo.result.EditBookmarkUrlResult;
import demo.tool.bookmark.pojo.result.GetBookmarkResult;
import demo.tool.bookmark.pojo.result.RemoveEmptyTagResult;
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
	
	@PostMapping(value = BookmarkUrl.DELETE_BOOKMARK_TAG)
	@ResponseBody
	public CommonResult deleteBookmarkTag(@RequestBody DeleteBookmarkTagDTO dto) {
		return service.deleteBookmarkTag(dto);
	}
	
	@PostMapping(value = BookmarkUrl.ADD_BOOKMARK_TAG)
	@ResponseBody
	public CreateBookmarkTagResult createBookmarkTag(@RequestBody CreateBookmarkTagDTO dto) {
		return service.createBookmarkTag(dto);
	}
	
	@PostMapping(value = BookmarkUrl.EDIT_BOOKMARK_TAG)
	@ResponseBody
	public CommonResult editBookmarkTag(@RequestBody EditBookmarkTagDTO dto) {
		return service.editBookmarkTag(dto);
	}
	
	@PostMapping(value = BookmarkUrl.EDIT_BOOKMARK_URL)
	@ResponseBody
	public EditBookmarkUrlResult editBookmarkUrl(@RequestBody EditBookmarkUrlDTO dto) {
		return service.editBookmarkUrl(dto);
	}
	
	@PostMapping(value = BookmarkUrl.DELETE_BOOKMARK_URL)
	@ResponseBody
	public CommonResult deleteBookmarkUrl(@RequestBody DeleteBookmarkUrlDTO dto) {
		return service.deleteBookmarkUrl(dto);
	}
	
	@PostMapping(value = BookmarkUrl.BOOKMARK_WEIGHT_CHANGE)
	@ResponseBody
	public CommonResult bookmarkWeightChange(@RequestBody BookmarkWeightChangeMainDTO dto) {
		return service.bookmarkWeightChange(dto);
	}
	
	@PostMapping(value = BookmarkUrl.REMOVE_EMPTY_TAG)
	@ResponseBody
	public RemoveEmptyTagResult editBookmarkUrl(@RequestBody RemoveEmptyTagDTO dto) {
		return service.removeEmptyTags(dto);
	}
	
	@PostMapping(value = BookmarkUrl.UPLOAD_BOOKMARK)
	@ResponseBody
	public CommonResult importFromBookmarkFile(@RequestBody UploadBookmarkDTO dto) {
		return service.importFromBookmarkFile(dto);
	}
}
