package demo.base.admin.service;

import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface AdminService {

	CommonResultCX deleteUserIpRecord(UserIpDeleteDTO param);

	void loadHomepageAnnouncementStr();

	void loadHomepageAnnouncementStr(String strContent);

}
