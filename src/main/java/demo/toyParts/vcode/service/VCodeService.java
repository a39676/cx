package demo.toyParts.vcode.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.toyParts.vcode.pojo.po.Vcode;

public interface VCodeService {

	Vcode findVCode(String vcode);

	CommonResult updateUseCount(Vcode vcode);

}
