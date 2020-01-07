package demo.base.system.mapper;

import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.po.HostnameExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HostnameMapper {
    long countByExample(HostnameExample example);

    int deleteByExample(HostnameExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Hostname record);

    int insertSelective(Hostname record);

    List<Hostname> selectByExample(HostnameExample example);

    Hostname selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Hostname record, @Param("example") HostnameExample example);

    int updateByExample(@Param("record") Hostname record, @Param("example") HostnameExample example);

    int updateByPrimaryKeySelective(Hostname record);

    int updateByPrimaryKey(Hostname record);
}