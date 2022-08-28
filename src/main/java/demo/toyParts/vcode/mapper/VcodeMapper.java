package demo.toyParts.vcode.mapper;

import demo.toyParts.vcode.pojo.po.Vcode;
import demo.toyParts.vcode.pojo.po.VcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface VcodeMapper {
    long countByExample(VcodeExample example);

    int deleteByExample(VcodeExample example);

    int deleteByPrimaryKey(Long codeId);

    int insert(Vcode row);

    int insertSelective(Vcode row);

    List<Vcode> selectByExampleWithRowbounds(VcodeExample example, RowBounds rowBounds);

    List<Vcode> selectByExample(VcodeExample example);

    Vcode selectByPrimaryKey(Long codeId);

    int updateByExampleSelective(@Param("row") Vcode row, @Param("example") VcodeExample example);

    int updateByExample(@Param("row") Vcode row, @Param("example") VcodeExample example);

    int updateByPrimaryKeySelective(Vcode row);

    int updateByPrimaryKey(Vcode row);
}