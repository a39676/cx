package demo.base.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.admin.service.AdminService;
import demo.base.system.service.impl.SystemCommonService;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.base.user.pojo.po.UserIpExample;
import demo.base.user.pojo.po.UserIpExample.Criteria;

@Service
public class AdminServiceImpl extends SystemCommonService implements AdminService {

	@Autowired
	private UserIpMapper userIpMapper;

	@Override
	public CommonResult deleteUserIpRecord(UserIpDeleteDTO param) {
		CommonResult result = new CommonResult();
		if (param.getStartDate() == null || param.getEndDate() == null) {
			result.setMessage("Error param");
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
		result.setIsSuccess();
		result.setMessage(String.valueOf(deleteCount));
		return result;
	}

	@Override
	public void setTempHomepageAnnouncement(String strContent) {
		systemOptionService.setTmpHomepageAnnouncementStr(strContent);
	}

}
