package demo.ai.aiChat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.interaction.wechat.mapper.WechatQrcodeDetailMapper;
import demo.interaction.wechat.mapper.WechatUserDetailMapper;
import demo.interaction.wechat.mapper.WechatUserFromQrcodeMapper;
import demo.interaction.wechat.pojo.po.WechatQrcodeDetail;
import demo.interaction.wechat.pojo.po.WechatQrcodeDetailExample;
import demo.interaction.wechat.pojo.po.WechatUserDetail;
import demo.interaction.wechat.pojo.po.WechatUserFromQrcode;
import demo.interaction.wechat.pojo.po.WechatUserFromQrcodeExample;
import demo.interaction.wechat.service.WechatSdkForInterService;
import wechatSdk.pojo.type.WechatOfficialAccountType;

/**
 *
 * For AI chat calling, to avoid dependency loop, will NOT autowired ANY Wechat
 * services
 *
 */
@Service
public class WechatSdkForInterServiceImpl extends CommonService implements WechatSdkForInterService {

	@Autowired
	private WechatQrcodeDetailMapper qrCodeMapper;
	@Autowired
	private WechatUserFromQrcodeMapper wechatUserFromQrCodeMapper;
	@Autowired
	private WechatUserDetailMapper wechatUserDetailMapper;

	@Override
	public List<Long> __getWechatUserIdListByQrCodeId(Long qrCodeId) {
		List<Long> wechatUserIdList = new ArrayList<>();
		if (qrCodeId == null) {
			return wechatUserIdList;
		}

		WechatUserFromQrcodeExample example = new WechatUserFromQrcodeExample();
		example.createCriteria().andQrcodeIdEqualTo(qrCodeId);
		List<WechatUserFromQrcode> poList = wechatUserFromQrCodeMapper.selectByExample(example);
		for (WechatUserFromQrcode po : poList) {
			wechatUserIdList.add(po.getWechatUserId());
		}

		return wechatUserIdList;
	}

	@Override
	public List<WechatQrcodeDetail> __getAllQrCode() {
		WechatQrcodeDetailExample example = new WechatQrcodeDetailExample();
		example.createCriteria().andIsDeleteEqualTo(false)
				.andSourceOfficialAccountEqualTo(WechatOfficialAccountType.SUI_SHOU.getCode());
		return qrCodeMapper.selectByExample(example);
	}

	@Override
	public String getWechatOpenIdByWechatUserId(Long wechatUserId) {
		WechatUserDetail po = wechatUserDetailMapper.selectByPrimaryKey(wechatUserId);
		if (po == null) {
			return null;
		}

		String openId = po.getOpenId();

		return openId;
	}
}
