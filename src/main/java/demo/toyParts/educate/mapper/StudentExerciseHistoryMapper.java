package demo.toyParts.educate.mapper;

import demo.toyParts.educate.pojo.po.StudentExerciseHistory;
import demo.toyParts.educate.pojo.po.StudentExerciseHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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