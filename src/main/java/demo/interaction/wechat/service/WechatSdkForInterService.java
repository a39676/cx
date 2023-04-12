package demo.interaction.wechat.service;

import java.util.List;

import demo.interaction.wechat.pojo.po.WechatQrcodeDetail;

public interface WechatSdkForInterService {

	List<Long> __getWechatUserIdListByQrCodeId(Long qrCodeId);

	List<WechatQrcodeDetail> __getAllQrCode();

}
