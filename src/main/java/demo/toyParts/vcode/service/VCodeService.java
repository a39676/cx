package demo.toyParts.vcode.service;

import demo.baseCommon.pojo.result.CommonResultCX;
import demo.toyParts.vcode.pojo.param.DeleteInvalidCodeParam;
import demo.toyParts.vcode.pojo.param.GetVcodeByValueParam;
import demo.toyParts.vcode.pojo.param.UpdateDeleteMarkParam;
import demo.toyParts.vcode.pojo.po.VCode;

public interface VCodeService {

	VCode findVCode(GetVcodeByValueParam param);

	int updateDeleteMark(UpdateDeleteMarkParam param);

	int deleteInvalidCode(DeleteInvalidCodeParam param);

	CommonResultCX insertVcode(VCode newVCode);

	CommonResultCX updateUseCount(VCode vcode);

}
