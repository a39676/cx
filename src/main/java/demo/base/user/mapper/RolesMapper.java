package demo.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.RolesExample;

public interface RolesMapper {
    long countByExample(RolesExample example);

	int deleteByExample(RolesExample example);

	int deleteByPrimaryKey(Long roleId);

	int insert(Roles record);

	int insertSelective(Roles record);

	List<Roles> selectByExample(RolesExample example);

	Roles selectByPrimaryKey(Long roleId);

	int updateByExampleSelective(@Param("record") Roles record, @Param("example") RolesExample example);

	int updateByExample(@Param("record") Roles record, @Param("example") RolesExample example);

	int updateByPrimaryKeySelective(Roles record);

	int updateByPrimaryKey(Roles record);

}