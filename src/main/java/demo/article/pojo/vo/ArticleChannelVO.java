package demo.article.pojo.vo;

import demo.article.pojo.bo.ArticleChannelUUIDBO;

public class ArticleChannelVO implements Comparable<ArticleChannelVO> {

	private String channelName;
	private String uuid;
	private String channelImage;
	private Integer weights;

	public String getChannelImage() {
		return channelImage;
	}

	public void setChannelImage(String channelImage) {
		this.channelImage = channelImage;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getWeights() {
		return weights;
	}

	public void setWeights(Integer weights) {
		this.weights = weights;
	}

	@Override
	public String toString() {
		return "ArticleChannelVO [channelName=" + channelName + ", uuid=" + uuid + ", weights=" + weights + "]";
	}

	public ArticleChannelVO buildByArticleUUIDChannelBO(ArticleChannelUUIDBO bo) {
		ArticleChannelVO vo = new ArticleChannelVO();
		vo.setChannelName(bo.getChannelName());
		vo.setUuid(bo.getUuid());
		vo.setWeights(bo.getWeights());
		vo.setChannelImage(bo.getImage());
		return vo;
	}

	@Override
	public int compareTo(ArticleChannelVO o) {
		if (o.weights == null || this.weights == null) {
			if (o.weights == null && this.weights == null) {
				return 0;
			} else if (o.weights == null) {
				return -1;
			} else if (this.weights == null) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (this.weights > o.weights) {
				return -1;
			} else if (this.weights < o.weights) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
