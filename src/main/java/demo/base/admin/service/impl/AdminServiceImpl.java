package demo.base.admin.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.admin.service.AdminService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.baseCommon.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AdminServiceImpl extends CommonService implements AdminService {
	
	@Autowired
	private UserIpMapper userIpMapper;
	@Autowired
	private FileUtilCustom ioUtil;
	
	@Override
	public CommonResultCX deleteUserIpRecord(UserIpDeleteDTO param) {
		CommonResultCX result = new CommonResultCX();
		if(param.getStartDate() == null || param.getEndDate() == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		int deleteCount = userIpMapper.deleteRecord(param);
		result.fillWithResult(ResultTypeCX.success);
		result.setMessage(String.valueOf(deleteCount));
		return result;
	}
	
	@Override
	public void loadHomepageAnnouncementStr() {
		String filePath = constantService.getValByName(SystemConstantStore.homepageAnnouncement);
		File f = new File(filePath);
		if(f.exists()) {
			String strContent = ioUtil.getStringFromFile(filePath);
			constantService.setValByName(SystemConstantStore.homepageAnnouncementStr, strContent);
		}
	}
	
	@Override
	public void loadHomepageAnnouncementStr(String strContent) {
		constantService.setValByName(SystemConstantStore.homepageAnnouncementStr, strContent);
	}
}
