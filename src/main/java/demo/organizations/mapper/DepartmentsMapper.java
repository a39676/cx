package demo.organizations.mapper;

import demo.organizations.pojo.po.Departments;
import demo.organizations.pojo.po.DepartmentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentsMapper {

	int deleteByPrimaryKey(Long id);

	Departments selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Departments record);

	int updateByPrimaryKey(Departments record);

	long countByExample(DepartmentsExample example);

    int deleteByExample(DepartmentsExample example);

    int insert(Departments record);

    int insertSelective(Departments record);

    List<Departments> selectByExample(DepartmentsExample example);

    int updateByExampleSelective(@Param("record") Departments record, @Param("example") DepartmentsExample example);

    int updateByExample(@Param("record") Departments record, @Param("example") DepartmentsExample example);
}