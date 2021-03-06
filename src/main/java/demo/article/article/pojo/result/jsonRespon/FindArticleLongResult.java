package demo.article.article.pojo.result.jsonRespon;

import demo.article.article.pojo.vo.ArticleLongVO;
import demo.common.pojo.result.CommonResultCX;

public class FindArticleLongResult extends CommonResultCX {

	private ArticleLongVO articleLongVO;

	private Long articleId = -1L;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public ArticleLongVO getArticleLongVO() {
		return articleLongVO;
	}

	public void setArticleLongVO(ArticleLongVO articleLongVO) {
		this.articleLongVO = articleLongVO;
	}

	@Override
	public String toString() {
		return "FindArticleLongResult [articleLongVO=" + articleLongVO + ", articleId=" + articleId + "]";
	}

}
