package demo.base.user.mapper;

import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.UserAttemptsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserAttemptsMapper {
    long countByExample(UserAttemptsExample example);

    int deleteByExample(UserAttemptsExample example);

    int insert(UserAttempts record);

    int insertSelective(UserAttempts record);

    List<UserAttempts> selectByExampleWithRowbounds(UserAttemptsExample example, RowBounds rowBounds);

    List<UserAttempts> selectByExample(UserAttemptsExample example);

    int updateByExampleSelective(@Param("record") UserAttempts record, @Param("example") UserAttemptsExample example);

    int updateByExample(@Param("record") UserAttempts record, @Param("example") UserAttemptsExample example);
}