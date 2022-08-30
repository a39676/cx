package demo.toyParts.educate.mapper;

import demo.toyParts.educate.pojo.po.StudentParent;
import demo.toyParts.educate.pojo.po.StudentParentExample;
import demo.toyParts.educate.pojo.po.StudentParentKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentParentMapper {
    long countByExample(StudentParentExample example);

    int deleteByExample(StudentParentExample example);

    int deleteByPrimaryKey(StudentParentKey key);

    int insert(StudentParent row);

    int insertSelective(StudentParent row);

    List<StudentParent> selectByExampleWithRowbounds(StudentParentExample example, RowBounds rowBounds);

    List<StudentParent> selectByExample(StudentParentExample example);

    StudentParent selectByPrimaryKey(StudentParentKey key);

    int updateByExampleSelective(@Param("row") StudentParent row, @Param("example") StudentParentExample example);

    int updateByExample(@Param("row") StudentParent row, @Param("example") StudentParentExample example);

    int updateByPrimaryKeySelective(StudentParent row);

    int updateByPrimaryKey(StudentParent row);
}