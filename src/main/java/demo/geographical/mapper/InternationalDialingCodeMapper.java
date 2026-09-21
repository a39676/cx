package demo.geographical.mapper;

import demo.geographical.pojo.po.InternationalDialingCode;
import demo.geographical.pojo.po.InternationalDialingCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface InternationalDialingCodeMapper {
    long countByExample(InternationalDialingCodeExample example);

    int deleteByExample(InternationalDialingCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InternationalDialingCode row);

    int insertSelective(InternationalDialingCode row);

    List<InternationalDialingCode> selectByExampleWithRowbounds(InternationalDialingCodeExample example, RowBounds rowBounds);

    List<InternationalDialingCode> selectByExample(InternationalDialingCodeExample example);

    InternationalDialingCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") InternationalDialingCode row, @Param("example") InternationalDialingCodeExample example);

    int updateByExample(@Param("row") InternationalDialingCode row, @Param("example") InternationalDialingCodeExample example);

    int updateByPrimaryKeySelective(InternationalDialingCode row);

    int updateByPrimaryKey(InternationalDialingCode row);
}