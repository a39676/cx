package demo.article.article.pojo.bo;

import java.util.List;

import demo.article.article.pojo.vo.ArticleChannelVO;

public class GetArticleChannelsBO {

	private List<ArticleChannelVO> publicChannels;
	private List<ArticleChannelVO> flashChannels;
	private List<ArticleChannelVO> privateChannels;

	public List<ArticleChannelVO> getPublicChannels() {
		return publicChannels;
	}

	public void setPublicChannels(List<ArticleChannelVO> publicChannels) {
		this.publicChannels = publicChannels;
	}

	public List<ArticleChannelVO> getFlashChannels() {
		return flashChannels;
	}

	public void setFlashChannels(List<ArticleChannelVO> flashChannels) {
		this.flashChannels = flashChannels;
	}

	public List<ArticleChannelVO> getPrivateChannels() {
		return privateChannels;
	}

	public void setPrivateChannels(List<ArticleChannelVO> privateChannels) {
		this.privateChannels = privateChannels;
	}

	@Override
	public String toString() {
		return "GetArticleChannelsResult [publicChannels=" + publicChannels + ", flashChannels=" + flashChannels
				+ ", privateChannels=" + privateChannels + "]";
	}

}
