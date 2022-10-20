package demo.tool.bookmark.service;

import org.springframework.web.servlet.ModelAndView;

import demo.tool.bookmark.pojo.dto.GetBookmarkWithPwdDTO;
import demo.tool.bookmark.pojo.result.GetBookmarkResult;

public interface BookmarkService {

	ModelAndView findAllBookmark();

	ModelAndView getBookmark(String pk);

	GetBookmarkResult getBookmark(GetBookmarkWithPwdDTO dto);

}
