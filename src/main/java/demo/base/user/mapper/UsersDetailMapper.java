package demo.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.dto.EnsureActiveEmailDTO;
import demo.base.user.pojo.dto.UpdateDuplicateEmailDTO;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.po.UsersDetailExample;

public interface UsersDetailMapper {
	long countByExample(UsersDetailExample example);

	int deleteByExample(UsersDetailExample example);

	int deleteByPrimaryKey(Long userId);

	int insert(UsersDetail record);

	int insertSelective(UsersDetail record);

	List<UsersDetail> selectByExample(UsersDetailExample example);

	UsersDetail selectByPrimaryKey(Long userId);

	int updateByExampleSelective(@Param("record") UsersDetail record, @Param("example") UsersDetailExample example);

	int updateByExample(@Param("record") UsersDetail record, @Param("example") UsersDetailExample example);

	int updateByPrimaryKeySelective(UsersDetail record);

	int updateByPrimaryKey(UsersDetail record);

    int isNickNameExists(String nickName);
    
    Long findUserIdByActivationEmail(EnsureActiveEmailDTO param);
    
    String findUserNameByActivationEmail(EnsureActiveEmailDTO param);
    
    String findEmailByUserId(Long userId);

	int modifyRegistEmail(@Param("email")String email, @Param("userId")Long userId);
	
	int updateDuplicateEmail(UpdateDuplicateEmailDTO param);
	
	String findHeadImage(@Param("userId")Long userId);
}