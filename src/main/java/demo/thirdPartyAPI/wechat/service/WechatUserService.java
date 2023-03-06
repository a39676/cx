package demo.thirdPartyAPI.wechat.service;

import demo.thirdPartyAPI.wechat.pojo.po.WechatUserDetail;

public interface WechatUserService {

	WechatUserDetail findWechatUserByUid(String uid);

}
