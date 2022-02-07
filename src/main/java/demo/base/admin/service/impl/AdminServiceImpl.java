package demo.base.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.admin.service.AdminService;
import demo.base.system.service.impl.SystemCommonService;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.base.user.pojo.po.UserIpExample;
import demo.base.user.pojo.po.UserIpExample.Criteria;
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

		UserIpExample example = new UserIpExample();
		Criteria criteria = example.createCriteria();
		if(param.getStartDate() != null) {
			criteria.andCreateTimeGreaterThanOrEqualTo(localDateTimeHandler.dateToLocalDateTime(param.getStartDate()));
		}
		if(param.getEndDate() != null) {
			criteria.andCreateTimeLessThanOrEqualTo((localDateTimeHandler.dateToLocalDateTime(param.getEndDate())));
		}
		if(StringUtils.isNotBlank(param.getUri())) {
			criteria.andUriEqualTo(param.getUri());
		}
		if(param.getUriList() != null && !param.getUriList().isEmpty()) {
			criteria.andUriIn(param.getUriList());
		}
		
		userIpMapper.deleteByExample(example);
		int deleteCount = userIpMapper.deleteByExample(example);
		result.fillWithResult(ResultTypeCX.success);
		result.setMessage(String.valueOf(deleteCount));
		return result;
	}

	@Override
	public void setTempHomepageAnnouncement(String strContent) {
		systemConstantService.setTmpHomepageAnnouncementStr(strContent);
	}

}
