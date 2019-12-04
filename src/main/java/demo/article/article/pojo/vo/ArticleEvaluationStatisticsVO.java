package demo.article.article.pojo.vo;

import java.util.HashMap;

import demo.article.article.pojo.type.ArticleEvaluationCodeType;

public class ArticleEvaluationStatisticsVO {

	private String pk;

	private HashMap<Integer, ArticleEvaluationCounterVO> evaluationCodeAndCount = new HashMap<Integer, ArticleEvaluationCounterVO>();

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public HashMap<Integer, ArticleEvaluationCounterVO> getEvaluationCodeAndCount() {
		return evaluationCodeAndCount;
	}

	public void setEvaluationCodeAndCount(HashMap<Integer, ArticleEvaluationCounterVO> evaluationCodeAndCount) {
		this.evaluationCodeAndCount = evaluationCodeAndCount;
	}

	public void addEvaluationCount(Integer evaluationCode, Integer evaluationCount) {
		ArticleEvaluationCodeType evaluationType = null;
		if(evaluationCodeAndCount.containsKey(evaluationCode)) {
			evaluationCodeAndCount.put(evaluationCode, evaluationCodeAndCount.get(evaluationCode).addEvaluationCount(evaluationCount));
		} else {
			evaluationType = ArticleEvaluationCodeType.getType(evaluationCode);
			if(evaluationType != null) {
				ArticleEvaluationCounterVO vo = new ArticleEvaluationCounterVO();
				vo.setEvaluationCode(evaluationCode);
				vo.setEvaluationName(evaluationType.getName());
				vo.setEvaluationCount(evaluationCount);
				evaluationCodeAndCount.put(evaluationCode, vo);
			}
		}
	}
}
