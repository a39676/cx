package demo.article.pojo.result;

import demo.article.pojo.po.ArticleBurn;
import demo.baseCommon.pojo.result.CommonResult;

public class CreatingBurnMessageResult extends CommonResult {

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
