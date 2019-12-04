package demo.base.organizations.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.base.organizations.pojo.po.DepartmentUser;
import demo.base.organizations.pojo.po.DepartmentUserExample;

public interface DepartmentUserMapper {
    long countByExample(DepartmentUserExample example);

    int deleteByExample(DepartmentUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DepartmentUser record);

    int insertSelective(DepartmentUser record);

    List<DepartmentUser> selectByExample(DepartmentUserExample example);

    DepartmentUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DepartmentUser record, @Param("example") DepartmentUserExample example);

    int updateByExample(@Param("record") DepartmentUser record, @Param("example") DepartmentUserExample example);

    int updateByPrimaryKeySelective(DepartmentUser record);

    int updateByPrimaryKey(DepartmentUser record);
}