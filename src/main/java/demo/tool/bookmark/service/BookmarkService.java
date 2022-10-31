package demo.tool.bookmark.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.bookmark.pojo.dto.BookmarkWeightChangeMainDTO;
import demo.tool.bookmark.pojo.dto.CreateBookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.CreateNewBookmarkDTO;
import demo.tool.bookmark.pojo.dto.DeleteBookmarkDTO;
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

public interface BookmarkService {

	ModelAndView findAllBookmark();

	ModelAndView getBookmark(String pk);

	GetBookmarkResult getBookmark(GetBookmarkWithPwdDTO dto);

	CommonResult createNewBookmark(CreateNewBookmarkDTO dto);

	CommonResult deleteBookmarkUrl(String bookmarkPk, String bookmarkUrlPk);

	CommonResult deleteBookmarkTag(DeleteBookmarkTagDTO dto);

	CreateBookmarkTagResult createBookmarkTag(CreateBookmarkTagDTO dto);

	CommonResult editBookmarkTag(EditBookmarkTagDTO dto);

	EditBookmarkUrlResult editBookmarkUrl(EditBookmarkUrlDTO dto);

	CommonResult deleteBookmarkUrl(DeleteBookmarkUrlDTO dto);

	CommonResult bookmarkWeightChange(BookmarkWeightChangeMainDTO dto);

	RemoveEmptyTagResult removeEmptyTags(RemoveEmptyTagDTO dto);

	void reBalanceWeight();

	CommonResult importFromBookmarkFile(UploadBookmarkDTO dto);

	CommonResult deleteBookmark(DeleteBookmarkDTO dto);

	void cleanOldFile();

}
