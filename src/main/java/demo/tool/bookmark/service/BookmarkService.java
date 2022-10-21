package demo.tool.bookmark.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.bookmark.pojo.dto.CreateBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.CreateNewBookmarkDTO;
import demo.tool.bookmark.pojo.dto.DeleteBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.EditBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.GetBookmarkWithPwdDTO;
import demo.tool.bookmark.pojo.result.CreateBookmarkTagResult;
import demo.tool.bookmark.pojo.result.GetBookmarkResult;

public interface BookmarkService {

	ModelAndView findAllBookmark();

	ModelAndView getBookmark(String pk);

	GetBookmarkResult getBookmark(GetBookmarkWithPwdDTO dto);

	CommonResult createNewBookmark(CreateNewBookmarkDTO dto);

	CommonResult deleteBookmarkUrl(String bookmarkPk, String bookmarkUrlPk);

	CommonResult deleteBookmarkTag(DeleteBookmarkTagDTO dto);

	CreateBookmarkTagResult createBookmarkTag(CreateBookmarkTagDTO dto);

	CommonResult editBookmarkTag(EditBookmarkTagDTO dto);

}
