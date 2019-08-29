package demo.articleComment.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class CreateArticleCommentParam implements CommonControllerParam {

	private String pk;

	private String content;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CreateArticleCommentParam [pk=" + pk + ", content=" + content + "]";
	}

	@Override
	public CreateArticleCommentParam fromJson(JSONObject json) {
		CreateArticleCommentParam param = new CreateArticleCommentParam();
		if (json.containsKey("pk")) {
			param.setPk(json.getString("pk"));
		}
		if (json.containsKey("content")) {
			param.setContent(json.getString("content"));
		}
		return param;
	}

}
