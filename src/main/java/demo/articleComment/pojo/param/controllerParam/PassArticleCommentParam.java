package demo.articleComment.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class PassArticleCommentParam implements CommonControllerParam {

	private Long commentId;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	@Override
	public String toString() {
		return "PassArticleCommentParam [commentId=" + commentId + "]";
	}

	@Override
	public PassArticleCommentParam fromJson(JSONObject json) {
		PassArticleCommentParam param = new PassArticleCommentParam();
		if (json.containsKey("commentId") && NumericUtilCustom.matchInteger(json.getString("commentId"))) {
			param.setCommentId(json.getLong("commentId"));
		}
		return param;
	}

}
