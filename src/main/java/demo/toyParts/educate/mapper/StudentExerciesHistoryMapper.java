package demo.toyParts.educate.mapper;

import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.po.StudentExerciesHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentExerciesHistoryMapper {
    long countByExample(StudentExerciesHistoryExample example);

    int deleteByExample(StudentExerciesHistoryExample example);

    int deleteByPrimaryKey(Long exerciesId);

    int insert(StudentExerciesHistory record);

    int insertSelective(StudentExerciesHistory record);

    List<StudentExerciesHistory> selectByExampleWithRowbounds(StudentExerciesHistoryExample example, RowBounds rowBounds);

    List<StudentExerciesHistory> selectByExample(StudentExerciesHistoryExample example);

    StudentExerciesHistory selectByPrimaryKey(Long exerciesId);

    int updateByExampleSelective(@Param("record") StudentExerciesHistory record, @Param("example") StudentExerciesHistoryExample example);

    int updateByExample(@Param("record") StudentExerciesHistory record, @Param("example") StudentExerciesHistoryExample example);

    int updateByPrimaryKeySelective(StudentExerciesHistory record);

    int updateByPrimaryKey(StudentExerciesHistory record);
}