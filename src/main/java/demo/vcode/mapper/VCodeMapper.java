package demo.vcode.mapper;

import demo.vcode.pojo.param.DeleteInvalidCodeParam;
import demo.vcode.pojo.param.GetVcodeByValueParam;
import demo.vcode.pojo.param.UpdateDeleteMarkParam;
import demo.vcode.pojo.po.VCode;

public interface VCodeMapper {
	int insert(VCode record);

    int insertSelective(VCode record);
    
    VCode getVCodeByValue(GetVcodeByValueParam param);
    
    int deleteInvalidCode(DeleteInvalidCodeParam param);
    
    int updateDeleteMark(UpdateDeleteMarkParam param);
    
    int updateUseCount(Long codeId);
}