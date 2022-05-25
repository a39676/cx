package demo.base.admin.service;

import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.common.pojo.result.CommonResultCX;

public interface AdminService {

	CommonResultCX deleteUserIpRecord(UserIpDeleteDTO param);

	void setTempHomepageAnnouncement(String strContent);

}
