package demo.tool.educate.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.tool.educate.pojo.po.StudentExerciseHistory;
import demo.tool.educate.pojo.po.StudentExerciseHistoryExample;

public interface StudentExerciseHistoryMapper {
    long countByExample(StudentExerciseHistoryExample example);

    int deleteByExample(StudentExerciseHistoryExample example);

    int deleteByPrimaryKey(Long exerciseId);

    int insert(StudentExerciseHistory row);

    int insertSelective(StudentExerciseHistory row);

    List<StudentExerciseHistory> selectByExampleWithRowbounds(StudentExerciseHistoryExample example, RowBounds rowBounds);

    List<StudentExerciseHistory> selectByExample(StudentExerciseHistoryExample example);

    StudentExerciseHistory selectByPrimaryKey(Long exerciseId);

    int updateByExampleSelective(@Param("row") StudentExerciseHistory row, @Param("example") StudentExerciseHistoryExample example);

    int updateByExample(@Param("row") StudentExerciseHistory row, @Param("example") StudentExerciseHistoryExample example);

    int updateByPrimaryKeySelective(StudentExerciseHistory row);

    int updateByPrimaryKey(StudentExerciseHistory row);
}