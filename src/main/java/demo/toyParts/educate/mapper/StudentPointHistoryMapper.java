package demo.toyParts.educate.mapper;

import demo.toyParts.educate.pojo.po.StudentPointHistory;
import demo.toyParts.educate.pojo.po.StudentPointHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentPointHistoryMapper {
    long countByExample(StudentPointHistoryExample example);

    int deleteByExample(StudentPointHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StudentPointHistory row);

    int insertSelective(StudentPointHistory row);

    List<StudentPointHistory> selectByExampleWithRowbounds(StudentPointHistoryExample example, RowBounds rowBounds);

    List<StudentPointHistory> selectByExample(StudentPointHistoryExample example);

    StudentPointHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") StudentPointHistory row, @Param("example") StudentPointHistoryExample example);

    int updateByExample(@Param("row") StudentPointHistory row, @Param("example") StudentPointHistoryExample example);

    int updateByPrimaryKeySelective(StudentPointHistory row);

    int updateByPrimaryKey(StudentPointHistory row);
}