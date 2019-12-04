package demo.article.article.pojo.param.mapperParam;

import java.util.List;

public class FindEvaluationStatisticsByIdListParam {

	private Integer evaluationType;

	private List<Long> postObjectIdList;

	public Integer getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Integer evaluationType) {
		this.evaluationType = evaluationType;
	}

	public List<Long> getPostObjectIdList() {
		return postObjectIdList;
	}

	public void setPostObjectIdList(List<Long> postObjectIdList) {
		this.postObjectIdList = postObjectIdList;
	}

}
