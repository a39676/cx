package demo.article.pojo.param.mapperParam;

import java.util.Date;

public class HasOldEvaluationParam {

	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "HasOldEvaluationParam [createTime=" + createTime + "]";
	}

}
