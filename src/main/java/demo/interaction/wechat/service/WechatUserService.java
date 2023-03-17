package demo.interaction.wechat.service;

import demo.interaction.wechat.pojo.po.WechatUserDetail;

public interface WechatUserService {

	WechatUserDetail findWechatUserByOpenId(String uid);

	Long findWechatLongIdByOpenId(String uid);

}
