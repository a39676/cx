package demo.toyParts.vcode.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.toyParts.vcode.pojo.param.DeleteInvalidCodeParam;
import demo.toyParts.vcode.pojo.param.UpdateDeleteMarkParam;
import demo.toyParts.vcode.pojo.po.VCode;

public interface VCodeService {

	VCode findVCode(String vcode);

	int updateDeleteMark(UpdateDeleteMarkParam param);

	int deleteInvalidCode(DeleteInvalidCodeParam param);

	CommonResult insertVcode(VCode newVCode);

	CommonResult updateUseCount(VCode vcode);

}
