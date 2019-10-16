package demo.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.base.user.pojo.po.UserIp;
import demo.base.user.pojo.po.UserIpExample;

public interface UserIpMapper {

	long countByExample(UserIpExample example);

    int deleteByExample(UserIpExample example);

    int insert(UserIp record);

    int insertSelective(UserIp record);

    List<UserIp> selectByExample(UserIpExample example);

    int updateByExampleSelective(@Param("record") UserIp record, @Param("example") UserIpExample example);

    int updateByExample(@Param("record") UserIp record, @Param("example") UserIpExample example);
    
    int deleteRecord(UserIpDeleteDTO param);
	
}