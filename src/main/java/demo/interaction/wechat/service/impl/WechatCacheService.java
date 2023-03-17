package demo.interaction.wechat.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class WechatCacheService extends CommonService {

	private Map<String, String> tmporaryKeyMatchOpenId = new HashMap<>();

	public Map<String, String> getTmporaryKeyMatchOpenId() {
		return tmporaryKeyMatchOpenId;
	}

	public void setTmporaryKeyMatchOpenId(Map<String, String> tmporaryKeyMatchOpenId) {
		this.tmporaryKeyMatchOpenId = tmporaryKeyMatchOpenId;
	}

	public String getOpenId(String tmporaryKey) {
		return tmporaryKeyMatchOpenId.get(tmporaryKey);
	}

}
