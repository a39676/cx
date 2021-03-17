package demo.base.admin.service;

import demo.base.admin.pojo.dto.SetSystemConstantDTO;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.common.pojo.result.CommonResultCX;

public interface AdminService {

	CommonResultCX deleteUserIpRecord(UserIpDeleteDTO param);

	void loadHomepageAnnouncementStr();

	void loadHomepageAnnouncementStr(String strContent);

	CommonResultCX setSystemConstant(SetSystemConstantDTO dto);

}
