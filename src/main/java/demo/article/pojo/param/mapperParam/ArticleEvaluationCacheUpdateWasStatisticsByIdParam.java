package demo.article.pojo.param.mapperParam;

import java.util.List;

public class ArticleEvaluationCacheUpdateWasStatisticsByIdParam {

	private List<Long> idList;

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	@Override
	public String toString() {
		return "ArticleEvaluationCacheDeleteByIdParam [idList=" + idList + "]";
	}

}