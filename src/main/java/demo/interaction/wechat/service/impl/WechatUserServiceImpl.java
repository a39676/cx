package demo.interaction.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.interaction.wechat.mapper.WechatUserDetailMapper;
import demo.interaction.wechat.pojo.po.WechatUserDetail;
import demo.interaction.wechat.pojo.po.WechatUserDetailExample;
import demo.interaction.wechat.service.WechatUserService;

@Service
public class WechatUserServiceImpl extends WechatCommonService implements WechatUserService {

	@Autowired
	private WechatUserDetailMapper wechatUserDetailMapper;
	
	private static final String FAKE_UID_PREFIX = "FakeUid_";
	
	@Override
	public WechatUserDetail findWechatUserByOpenId(String uid) {
		WechatUserDetailExample example = new WechatUserDetailExample();
		example.createCriteria().andUnionIdEqualTo(uid);
		List<WechatUserDetail> wechatUserList = wechatUserDetailMapper.selectByExample(example);
		if(!wechatUserList.isEmpty()) {
			return null;
		}
		return wechatUserList.get(0);
	}
	
	@Override
	public Long findWechatLongIdByOpenId(String uid) {
		WechatUserDetailExample example = new WechatUserDetailExample();
		example.createCriteria().andUnionIdEqualTo(uid);
		List<WechatUserDetail> wechatUserList = wechatUserDetailMapper.selectByExample(example);
		if(!wechatUserList.isEmpty()) {
			return null;
		}
		WechatUserDetail po = wechatUserList.get(0);
		return po.getId();
	}
}
