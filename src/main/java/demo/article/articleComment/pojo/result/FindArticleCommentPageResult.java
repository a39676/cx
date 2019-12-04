package demo.article.articleComment.pojo.result;

import java.util.List;

import demo.article.article.pojo.vo.ArticleCommentVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public class FindArticleCommentPageResult extends CommonResultCX {

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
