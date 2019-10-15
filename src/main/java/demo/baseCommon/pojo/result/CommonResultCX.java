package demo.baseCommon.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.BaseResultType;
import demo.baseCommon.pojo.type.ResultTypeCX;

public class CommonResultCX extends CommonResult {

	public void fillWithResult(ResultTypeCX resultType) {
		this.setResult(resultType.getCode());
		this.setMessage(resultType.getName());
		if(BaseResultType.success.getCode().equals(resultType.getCode())) {
			this.success = true;
		} else {
			this.success = false;
		}
	}
	
}