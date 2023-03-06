package demo.thirdPartyAPI.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.thirdPartyAPI.wechat.mapper.WechatUserDetailMapper;
import demo.thirdPartyAPI.wechat.pojo.po.WechatUserDetail;
import demo.thirdPartyAPI.wechat.pojo.po.WechatUserDetailExample;
import demo.thirdPartyAPI.wechat.service.WechatUserService;

@Service
public class WechatUserServiceImpl extends WechatCommonService implements WechatUserService {

	@Autowired
	private WechatUserDetailMapper wechatUserDetailMapper;
	
	@Override
	public WechatUserDetail findWechatUserByUid(String uid) {
		WechatUserDetailExample example = new WechatUserDetailExample();
		example.createCriteria().andUnionIdEqualTo(uid);
		List<WechatUserDetail> wechatUserList = wechatUserDetailMapper.selectByExample(example);
		if(!wechatUserList.isEmpty()) {
			return null;
		}
		return wechatUserList.get(0);
	}
}
