package demo.article.article.pojo.result;

import demo.article.article.pojo.po.ArticleBurn;
import demo.baseCommon.pojo.result.CommonResultCX;

public class CreatingBurnMessageResult_backup extends CommonResultCX {

	private ArticleBurn articleBurn;

	public ArticleBurn getArticleBurn() {
		return articleBurn;
	}

	public void setArticleBurn(ArticleBurn articleBurn) {
		this.articleBurn = articleBurn;
	}

	@Override
	public String toString() {
		return "CreatingBurnMessageResult [articleBurn=" + articleBurn + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
