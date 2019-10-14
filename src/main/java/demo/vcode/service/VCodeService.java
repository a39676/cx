package demo.vcode.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.vcode.pojo.param.DeleteInvalidCodeParam;
import demo.vcode.pojo.param.GetVcodeByValueParam;
import demo.vcode.pojo.param.UpdateDeleteMarkParam;
import demo.vcode.pojo.po.VCode;

public interface VCodeService {

	VCode findVCode(GetVcodeByValueParam param);

	int updateDeleteMark(UpdateDeleteMarkParam param);

	int deleteInvalidCode(DeleteInvalidCodeParam param);

	CommonResult insertVcode(VCode newVCode);

	CommonResult updateUseCount(VCode vcode);

}
