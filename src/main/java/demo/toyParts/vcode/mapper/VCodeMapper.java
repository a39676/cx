package demo.toyParts.vcode.mapper;

import demo.toyParts.vcode.pojo.param.DeleteInvalidCodeParam;
import demo.toyParts.vcode.pojo.param.UpdateDeleteMarkParam;
import demo.toyParts.vcode.pojo.po.VCode;

public interface VCodeMapper {
	int insert(VCode record);

    int insertSelective(VCode record);
    
    VCode getVCodeByValue(String vcode);
    
    int deleteInvalidCode(DeleteInvalidCodeParam param);
    
    int updateDeleteMark(UpdateDeleteMarkParam param);
    
    int updateUseCount(Long codeId);
}