package demo.article.pojo.param.mapperParam;

import java.util.ArrayList;
import java.util.List;

public class UpdateChannelIsFlashParam {

	private boolean isFlash = false;
	private List<Long> channelIdList = new ArrayList<Long>();

	public boolean isFlash() {
		return isFlash;
	}

	public void setFlash(boolean isFlash) {
		this.isFlash = isFlash;
	}

	public List<Long> getChannelIdList() {
		return channelIdList;
	}

	public void setChannelIdList(List<Long> channelIdList) {
		this.channelIdList = channelIdList;
	}
	
	public void addChannelId(Long channelId) {
		this.channelIdList.add(channelId);
	}

	@Override
	public String toString() {
		return "UpdateChannelIsFlashParam [isFlash=" + isFlash + ", channelIdList=" + channelIdList + "]";
	}

}
