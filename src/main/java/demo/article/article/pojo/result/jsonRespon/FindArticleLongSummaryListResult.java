package demo.article.article.pojo.result.jsonRespon;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.vo.ArticleLongSummaryVO;

public class FindArticleLongSummaryListResult extends CommonResult {

	private List<ArticleLongSummaryVO> articleLongSummaryVOList;

	private Long channelId = -1L;

	public List<ArticleLongSummaryVO> getArticleLongSummaryVOList() {
		return articleLongSummaryVOList;
	}

	public void setArticleLongSummaryVOList(List<ArticleLongSummaryVO> articleLongSummaryVOList) {
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
