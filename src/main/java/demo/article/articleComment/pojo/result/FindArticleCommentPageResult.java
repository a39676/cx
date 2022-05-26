package demo.article.articleComment.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.articleComment.pojo.vo.ArticleCommentVO;

public class FindArticleCommentPageResult extends CommonResult {

	private List<ArticleCommentVO> commentList;

	private String pk;

	public List<ArticleCommentVO> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<ArticleCommentVO> commentList) {
		this.commentList = commentList;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "FindArticleCommentPageResult [commentList=" + commentList + ", pk=" + pk + "]";
	}

}
