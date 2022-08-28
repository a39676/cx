package demo.toyParts.vcode.mapper;

import demo.toyParts.vcode.pojo.po.VcodeHistory;
import demo.toyParts.vcode.pojo.po.VcodeHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface VcodeHistoryMapper {
    long countByExample(VcodeHistoryExample example);

    int deleteByExample(VcodeHistoryExample example);

    int insert(VcodeHistory row);

    int insertSelective(VcodeHistory row);

    List<VcodeHistory> selectByExampleWithRowbounds(VcodeHistoryExample example, RowBounds rowBounds);

    List<VcodeHistory> selectByExample(VcodeHistoryExample example);

    int updateByExampleSelective(@Param("row") VcodeHistory row, @Param("example") VcodeHistoryExample example);

    int updateByExample(@Param("row") VcodeHistory row, @Param("example") VcodeHistoryExample example);
}