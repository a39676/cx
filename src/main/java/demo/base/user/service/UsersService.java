package demo.base.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.dto.FindUserByConditionDTO;
import demo.base.user.pojo.dto.OtherUserInfoDTO;
import demo.base.user.pojo.dto.UserAttemptQuerayDTO;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.result.FindUserByConditionResult;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.pojo.vo.UsersDetailVO;


/**
 * @author Acorn
 * 2017年4月13日
 */
public interface UsersService {

	int insertFailAttempts(String userName);

	int setLockeds(Users user);
	
	int resetFailAttempts(String userName);

	Long getUserIdByUserName(String userName);
	
	/**
	 * 可输入 userName || userId(better)
	 * 2017年4月13日
	 * @param userName
	 * @param userId
	 * @return
	 * ArrayList<UserAttempts>
	 */
	ArrayList<UserAttempts> getUserAttempts(UserAttemptQuerayDTO param);
	
	Users getUserbyUserName(String userName);

	UsersDetailVO findUserDetail(Long userId);
	String findHeadImageUrl(Long userId);

	int countAttempts(String userName);

	UsersDetailVO findOtherUserDetail(OtherUserInfoDTO param);

	MyUserPrincipal buildMyUserPrincipalByUserName(String userName);

	FindUserByConditionResult findUserByCondition(FindUserByConditionDTO dto);

	ModelAndView findUserInfo();

	List<Users> findUserListByRole(SystemRolesType systemRoleType);

}
