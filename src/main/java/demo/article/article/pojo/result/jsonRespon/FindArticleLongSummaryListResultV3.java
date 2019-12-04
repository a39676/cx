package demo.article.article.pojo.result.jsonRespon;

import java.util.List;

import demo.article.article.pojo.vo.ArticleLongSummaryVOV3;
import demo.baseCommon.pojo.result.CommonResultCX;

public class FindArticleLongSummaryListResultV3 extends CommonResultCX {

	private List<ArticleLongSummaryVOV3> articleLongSummaryVOList;

	private Long channelId = -1L;

	public List<ArticleLongSummaryVOV3> getArticleLongSummaryVOList() {
		return articleLongSummaryVOList;
	}

	public void setArticleLongSummaryVOList(List<ArticleLongSummaryVOV3> articleLongSummaryVOList) {
		this.articleLongSummaryVOList = articleLongSummaryVOList;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@Override
	public String toString() {
		return "FindArticleLongSummaryListResultV3 [articleLongSummaryVOList=" + articleLongSummaryVOList
				+ ", channelId=" + channelId + "]";
	}

}
