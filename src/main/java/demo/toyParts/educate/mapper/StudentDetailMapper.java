package demo.toyParts.educate.mapper;

import demo.toyParts.educate.pojo.po.StudentDetail;
import demo.toyParts.educate.pojo.po.StudentDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentDetailMapper {
    long countByExample(StudentDetailExample example);

    int deleteByExample(StudentDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StudentDetail record);

    int insertSelective(StudentDetail record);

    List<StudentDetail> selectByExampleWithRowbounds(StudentDetailExample example, RowBounds rowBounds);

    List<StudentDetail> selectByExample(StudentDetailExample example);

    StudentDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StudentDetail record, @Param("example") StudentDetailExample example);

    int updateByExample(@Param("record") StudentDetail record, @Param("example") StudentDetailExample example);

    int updateByPrimaryKeySelective(StudentDetail record);

    int updateByPrimaryKey(StudentDetail record);
}