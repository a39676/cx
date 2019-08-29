package demo.articleComment.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class DeleteArticleCommentParam implements CommonControllerParam {

	private Long commentId;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	@Override
	public String toString() {
		return "DeleteArticleCommentParam [commentId=" + commentId + "]";
	}

	@Override
	public DeleteArticleCommentParam fromJson(JSONObject json) {
		DeleteArticleCommentParam param = new DeleteArticleCommentParam();
		if (json.containsKey("commentId") && NumericUtilCustom.matchInteger(json.getString("commentId"))) {
			param.setCommentId(json.getLong("commentId"));
		}
		return param;
	}

}
