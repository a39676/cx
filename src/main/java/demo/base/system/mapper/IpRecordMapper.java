package demo.base.system.mapper;

import demo.base.system.pojo.po.IpRecord;
import demo.base.system.pojo.po.IpRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IpRecordMapper {
    long countByExample(IpRecordExample example);

    int deleteByExample(IpRecordExample example);

    int deleteByPrimaryKey(Long ip);

    int insert(IpRecord record);

    int insertSelective(IpRecord record);

    List<IpRecord> selectByExample(IpRecordExample example);

    IpRecord selectByPrimaryKey(Long ip);

    int updateByExampleSelective(@Param("record") IpRecord record, @Param("example") IpRecordExample example);

    int updateByExample(@Param("record") IpRecord record, @Param("example") IpRecordExample example);

    int updateByPrimaryKeySelective(IpRecord record);

    int updateByPrimaryKey(IpRecord record);
}