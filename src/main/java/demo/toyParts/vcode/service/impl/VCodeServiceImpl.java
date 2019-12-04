package demo.toyParts.vcode.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dateTimeHandle.DateUtilCustom;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.toyParts.vcode.mapper.VCodeMapper;
import demo.toyParts.vcode.pojo.param.DeleteInvalidCodeParam;
import demo.toyParts.vcode.pojo.param.GetVcodeByValueParam;
import demo.toyParts.vcode.pojo.param.UpdateDeleteMarkParam;
import demo.toyParts.vcode.pojo.param.type.VCodeType;
import demo.toyParts.vcode.pojo.po.VCode;
import demo.toyParts.vcode.service.VCodeService;

@Service
public class VCodeServiceImpl implements VCodeService {
	
	@Autowired
	private VCodeMapper vCodeMapper;
	
	@Override
	public VCode findVCode(GetVcodeByValueParam param) {
		if(param == null || StringUtils.isBlank(param.getCodeValue())) {
			return null;
		}
		
		return vCodeMapper.getVCodeByValue(param);
	}
	
	@Override
	public int updateDeleteMark(UpdateDeleteMarkParam param) {
		if(param == null) {
			return 0;
		}
		return vCodeMapper.updateDeleteMark(param);
	}
	
	@Override
	public int deleteInvalidCode(DeleteInvalidCodeParam param) {
		if(param == null) {
			return 0;
		}
		return vCodeMapper.deleteInvalidCode(param);
	}

	@Override
	public CommonResultCX insertVcode(VCode newVCode) {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(newVCode.getCodeValue())
				|| newVCode.getValidTime().getTime() <= System.currentTimeMillis()
				|| newVCode.getCodeType() == null
				|| VCodeType.getType(newVCode.getCodeType()) == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		if(newVCode.getValidTime() == null) {
			newVCode.setValidTime(DateUtilCustom.dateDiffDays(3));
		}
		if(newVCode.getCreateTime() == null) {
			newVCode.setCreateTime(new Date());
		}
		newVCode.setIsDelete(false);
		
		int insertCount = vCodeMapper.insertSelective(newVCode);
		if(insertCount == 1) {
			result.fillWithResult(ResultTypeCX.success);
			return result;
		} else {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
	}
	
	@Override
	public CommonResultCX updateUseCount(VCode vcode) {
		CommonResultCX result = new CommonResultCX();
		if(vcode == null || vcode.getCodeId() == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		vCodeMapper.updateUseCount(vcode.getCodeId());
		result.setIsSuccess();
		return result;
	}
}
