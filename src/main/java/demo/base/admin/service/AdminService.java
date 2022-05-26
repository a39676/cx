package demo.base.admin.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.dto.UserIpDeleteDTO;

public interface AdminService {

	CommonResult deleteUserIpRecord(UserIpDeleteDTO param);

	void setTempHomepageAnnouncement(String strContent);

}
