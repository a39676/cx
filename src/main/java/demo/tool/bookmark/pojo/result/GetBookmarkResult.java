package demo.tool.bookmark.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.bookmark.pojo.vo.BookmarkVO;

public class GetBookmarkResult extends CommonResult {

	private BookmarkVO bookmark;

	public BookmarkVO getBookmark() {
		return bookmark;
	}

	public void setBookmark(BookmarkVO bookmark) {
		this.bookmark = bookmark;
	}

	@Override
	public String toString() {
		return "GetBookmarkResult [bookmark=" + bookmark + "]";
	}

}
