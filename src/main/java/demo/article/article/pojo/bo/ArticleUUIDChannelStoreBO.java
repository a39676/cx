package demo.article.article.pojo.bo;

import java.util.Date;
import java.util.List;

public class ArticleUUIDChannelStoreBO {

	private List<ArticleChannelUUIDBO> channelList = null;

	private Date listCreateDate;

	public List<ArticleChannelUUIDBO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<ArticleChannelUUIDBO> channelList) {
		this.channelList = channelList;
	}

	public Date getListCreateDate() {
		return listCreateDate;
	}

	public void setListCreateDate(Date listCreateDate) {
		this.listCreateDate = listCreateDate;
	}
	
	public Long getChannelId(String uuid) {
		if(channelList == null || channelList.size() < 1) {
			return null;
		}
		for(ArticleChannelUUIDBO channel : channelList) {
			if(channel.getUuid().equals(uuid)) {
				return channel.getChannelId();
			}
		}
		return null;
	}
	
	public String getUUID(Long channelId) {
		if(channelId == null || channelList == null || channelList.size() < 1) {
			return null;
		}
		for(ArticleChannelUUIDBO channel : channelList) {
			if(channel.getChannelId().equals(channelId)) {
				return channel.getUuid();
			}
		}
		return null;
	}
	
	public String getUUID(String channelName) {
		if(channelName == null || channelList == null || channelList.size() < 1) {
			return null;
		}
		for(ArticleChannelUUIDBO channel : channelList) {
			if(channel.getChannelName().equals(channelName)) {
				return channel.getUuid();
			}
		}
		return null;
	}

}
