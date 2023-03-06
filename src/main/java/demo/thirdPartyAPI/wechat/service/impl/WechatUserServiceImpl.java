package demo.thirdPartyAPI.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.thirdPartyAPI.wechat.mapper.WechatOidUidMapper;
import demo.thirdPartyAPI.wechat.pojo.po.WechatOidUid;
import demo.thirdPartyAPI.wechat.pojo.po.WechatOidUidExample;
import demo.thirdPartyAPI.wechat.service.WechatUserService;

@Service
public class WechatUserServiceImpl extends CommonService implements WechatUserService {

	@Autowired
	private WechatOidUidMapper wechatIdMapper;
	
	@Override
	public WechatOidUid findWechatUserByUid(String uid) {
		WechatOidUidExample example = new WechatOidUidExample();
		example.createCriteria().andUnionIdEqualTo(uid);
		List<WechatOidUid> wechatUserList = wechatIdMapper.selectByExample(example);
		if(!wechatUserList.isEmpty()) {
			return null;
		}
		return wechatUserList.get(0);
	}
}
