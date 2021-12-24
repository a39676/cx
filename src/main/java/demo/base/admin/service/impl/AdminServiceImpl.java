package demo.base.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.admin.service.AdminService;
import demo.base.system.service.impl.SystemCommonService;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;

@Service
public class AdminServiceImpl extends SystemCommonService implements AdminService {

	@Autowired
	private UserIpMapper userIpMapper;

	@Override
	public CommonResultCX deleteUserIpRecord(UserIpDeleteDTO param) {
		CommonResultCX result = new CommonResultCX();
		if (param.getStartDate() == null || param.getEndDate() == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}

		int deleteCount = userIpMapper.deleteRecord(param);
		result.fillWithResult(ResultTypeCX.success);
		result.setMessage(String.valueOf(deleteCount));
		return result;
	}

	@Override
	public void setTempHomepageAnnouncement(String strContent) {
		systemConstantService.setTmpHomepageAnnouncementStr(strContent);
	}

}
