package demo.pmemo.mapper;

import demo.pmemo.pojo.po.PUrgeNote;
import demo.pmemo.pojo.po.PUrgeNoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PUrgeNoteMapper {
    long countByExample(PUrgeNoteExample example);

    int deleteByExample(PUrgeNoteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PUrgeNote row);

    int insertSelective(PUrgeNote row);

    List<PUrgeNote> selectByExampleWithRowbounds(PUrgeNoteExample example, RowBounds rowBounds);

    List<PUrgeNote> selectByExample(PUrgeNoteExample example);

    PUrgeNote selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") PUrgeNote row, @Param("example") PUrgeNoteExample example);

    int updateByExample(@Param("row") PUrgeNote row, @Param("example") PUrgeNoteExample example);

    int updateByPrimaryKeySelective(PUrgeNote row);

    int updateByPrimaryKey(PUrgeNote row);
}