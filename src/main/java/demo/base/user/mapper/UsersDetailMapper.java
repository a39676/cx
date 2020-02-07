package demo.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.bo.UserMailAndMailKeyBO;
import demo.base.user.pojo.dto.FindActiveEmailDTO;
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
    
    int isActiveEmailExists(FindActiveEmailDTO param);
    
    Long findUserIdByActivationEmail(FindActiveEmailDTO param);
    
    String findUserNameByActivationEmail(FindActiveEmailDTO param);
    
    String findEmailByUserId(Long userId);

//	UsersDetail findUserDetail(Long userId);
//	
//	UsersDetail findUserDetailByNickName(String userName);
	
	int modifyRegistEmail(@Param("email")String email, @Param("userId")Long userId);
	
	List<UserMailAndMailKeyBO> findUserEmailAndKey();

	int updateDuplicateEmail(UpdateDuplicateEmailDTO param);
	
	String findHeadImage(@Param("userId")Long userId);
}