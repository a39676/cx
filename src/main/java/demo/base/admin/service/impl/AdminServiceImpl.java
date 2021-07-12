package demo.base.admin.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.admin.pojo.dto.SetSystemConstantDTO;
import demo.base.admin.service.AdminService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.user.mapper.UserIpMapper;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
import demo.common.service.CommonService;
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
		String filePath = systemConstantService.getSysValByName(SystemConstantStore.homepageAnnouncement);
		File f = new File(filePath);
		if(f.exists()) {
			String strContent = ioUtil.getStringFromFile(filePath);
			redisConnectService.setValByName(SystemConstantStore.homepageAnnouncementStr, strContent);
		}
	}
	
	@Override
	public void loadHomepageAnnouncementStr(String strContent) {
		redisConnectService.setValByName(SystemConstantStore.homepageAnnouncementStr, strContent);
	}
	
	@Override
	public CommonResultCX setSystemConstant(SetSystemConstantDTO dto) {
		CommonResultCX r = new CommonResultCX();
		
		if(StringUtils.isBlank(dto.getKey())) {
			r.addMessage("can not set empty key");
			return r;
		}
		
		Boolean hasKeyFlag = redisConnectService.hasKey(dto.getKey());
		if(dto.getValidTimeSeconds() != null && dto.getValidTimeSeconds() > 0) {
			redisConnectService.setValByName(dto.getKey(), dto.getValue(), dto.getValidTimeSeconds(), TimeUnit.SECONDS);
		} else {
			redisConnectService.setValByName(dto.getKey(), dto.getValue());
		}
		
		if(hasKeyFlag) {
			r.addMessage("refresh key :");
		} else {
			r.addMessage("add key: ");
		}
		r.addMessage(dto.getKey());
		r.addMessage(", set: " + dto.getValue());
		
		if(dto.getValidTimeSeconds() != null && dto.getValidTimeSeconds() > 0) {
			LocalDateTime validTime = LocalDateTime.now().plusSeconds(dto.getValidTimeSeconds());
			r.addMessage(", vallid seconds: " + dto.getValidTimeSeconds() + ", till: " + localDateTimeHandler.dateToStr(validTime));;
		}
		
		r.setIsSuccess();
		return r;
	}
}
