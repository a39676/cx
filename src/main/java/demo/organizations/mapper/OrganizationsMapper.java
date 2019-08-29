package demo.organizations.mapper;

import demo.organizations.pojo.po.Organizations;
import demo.organizations.pojo.po.OrganizationsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrganizationsMapper {

	int deleteByPrimaryKey(Long id);

	Organizations selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Organizations record);

	int updateByPrimaryKey(Organizations record);

	long countByExample(OrganizationsExample example);

	int deleteByExample(OrganizationsExample example);

	int insert(Organizations record);

	int insertSelective(Organizations record);

	List<Organizations> selectByExample(OrganizationsExample example);

	int updateByExampleSelective(@Param("record") Organizations record, @Param("example") OrganizationsExample example);

	int updateByExample(@Param("record") Organizations record, @Param("example") OrganizationsExample example);
	
}