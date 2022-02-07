package demo.base.user.mapper;

import demo.base.user.pojo.po.UserIp;
import demo.base.user.pojo.po.UserIpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserIpMapper {
    long countByExample(UserIpExample example);

    int deleteByExample(UserIpExample example);

    int insert(UserIp record);

    int insertSelective(UserIp record);

    List<UserIp> selectByExampleWithRowbounds(UserIpExample example, RowBounds rowBounds);

    List<UserIp> selectByExample(UserIpExample example);

    int updateByExampleSelective(@Param("record") UserIp record, @Param("example") UserIpExample example);

    int updateByExample(@Param("record") UserIp record, @Param("example") UserIpExample example);

	void batchInsert(List<UserIp> poList);
}