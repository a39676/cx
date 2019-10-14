package demo.vcode.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import dateTimeHandle.DateUtilCustom;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.vcode.mapper.VCodeMapper;
import demo.vcode.pojo.param.DeleteInvalidCodeParam;
import demo.vcode.pojo.param.GetVcodeByValueParam;
import demo.vcode.pojo.param.UpdateDeleteMarkParam;
import demo.vcode.pojo.param.type.VCodeType;
import demo.vcode.pojo.po.VCode;
import demo.vcode.service.VCodeService;

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
	public CommonResult insertVcode(VCode newVCode) {
		CommonResult result = new CommonResult();
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
	public CommonResult updateUseCount(VCode vcode) {
		CommonResult result = new CommonResult();
		if(vcode == null || vcode.getCodeId() == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		vCodeMapper.updateUseCount(vcode.getCodeId());
		result.setIsSuccess();
		return result;
	}
}
