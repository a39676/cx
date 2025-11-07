package demo.article.article.pojo.vo;

import java.util.List;
import java.util.Objects;

import demo.article.article.pojo.dto.ArticleChannelKeyHostnameIdDTO;

public class ArticleChannelVO implements Comparable<ArticleChannelVO> {

	private String channelName;
	private String channelId;
	private String channelImage;
	private Integer weights;
	private boolean isDelete = false;
	/** ArticleChannelType */
	private Integer channelType;
	private String channelTypeName;
	private List<ArticleChannelKeyHostnameIdDTO> channelIdKeyHostnameId;

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}

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

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getWeights() {
		return weights;
	}

	public void setWeights(Integer weights) {
		this.weights = weights;
	}

	public List<ArticleChannelKeyHostnameIdDTO> getChannelIdKeyHostnameId() {
		return channelIdKeyHostnameId;
	}

	public void setChannelIdKeyHostnameId(List<ArticleChannelKeyHostnameIdDTO> channelIdKeyHostnameId) {
		this.channelIdKeyHostnameId = channelIdKeyHostnameId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(channelId, channelIdKeyHostnameId, channelImage, channelName, channelType, channelTypeName,
				isDelete, weights);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleChannelVO other = (ArticleChannelVO) obj;
		return Objects.equals(channelId, other.channelId)
				&& Objects.equals(channelIdKeyHostnameId, other.channelIdKeyHostnameId)
				&& Objects.equals(channelImage, other.channelImage) && Objects.equals(channelName, other.channelName)
				&& Objects.equals(channelType, other.channelType)
				&& Objects.equals(channelTypeName, other.channelTypeName) && isDelete == other.isDelete
				&& Objects.equals(weights, other.weights);
	}

	@Override
	public String toString() {
		return "ArticleChannelVO [channelName=" + channelName + ", channelId=" + channelId + ", channelImage="
				+ channelImage + ", weights=" + weights + ", isDelete=" + isDelete + ", channelType=" + channelType
				+ ", channelTypeName=" + channelTypeName + ", channelIdKeyHostnameId=" + channelIdKeyHostnameId + "]";
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
