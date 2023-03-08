package demo.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.po.UsersDetailExample;

public interface UsersDetailMapper {
	long countByExample(UsersDetailExample example);

	int deleteByExample(UsersDetailExample example);

	int deleteByPrimaryKey(Long userId);

	int insert(UsersDetail row);

	int insertSelective(UsersDetail row);

	List<UsersDetail> selectByExampleWithRowbounds(UsersDetailExample example, RowBounds rowBounds);

	List<UsersDetail> selectByExample(UsersDetailExample example);

	UsersDetail selectByPrimaryKey(Long userId);

	int updateByExampleSelective(@Param("row") UsersDetail row, @Param("example") UsersDetailExample example);

	int updateByExample(@Param("row") UsersDetail row, @Param("example") UsersDetailExample example);

	int updateByPrimaryKeySelective(UsersDetail row);

	int updateByPrimaryKey(UsersDetail row);

	int isNickNameExists(String nickName);

	String findEmailByUserId(Long userId);

	int modifyRegistEmail(@Param("email") String email, @Param("userId") Long userId);

	String findHeadImage(@Param("userId") Long userId);
}