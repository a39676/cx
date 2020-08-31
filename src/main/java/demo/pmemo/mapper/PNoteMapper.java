package demo.pmemo.mapper;

import demo.pmemo.pojo.po.PNote;
import demo.pmemo.pojo.po.PNoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PNoteMapper {
    long countByExample(PNoteExample example);

    int deleteByExample(PNoteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PNote record);

    int insertSelective(PNote record);

    List<PNote> selectByExampleWithRowbounds(PNoteExample example, RowBounds rowBounds);

    List<PNote> selectByExample(PNoteExample example);

    PNote selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PNote record, @Param("example") PNoteExample example);

    int updateByExample(@Param("record") PNote record, @Param("example") PNoteExample example);

    int updateByPrimaryKeySelective(PNote record);

    int updateByPrimaryKey(PNote record);
}