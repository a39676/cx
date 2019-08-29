package demo.article.pojo.param.mapperParam;

import java.util.List;

public class ArticleEvaluationCacheUpdateIsDeleteParam {

	private List<Long> idList;

	private boolean isDelete = false;

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "ArticleEvaluationCacheUpdateIsDeleteParam [idList=" + idList + ", isDelete=" + isDelete + "]";
	}

}