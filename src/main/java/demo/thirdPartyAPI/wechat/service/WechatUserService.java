package demo.thirdPartyAPI.wechat.service;

import demo.thirdPartyAPI.wechat.pojo.po.WechatOidUid;

public interface WechatUserService {

	WechatOidUid findWechatUserByUid(String uid);

}
