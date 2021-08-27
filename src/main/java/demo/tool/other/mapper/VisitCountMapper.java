package demo.tool.other.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.tool.other.pojo.dto.GetVisitCountTotalDTO;
import demo.tool.other.pojo.po.VisitCount;
import demo.tool.other.pojo.po.VisitCountExample;

public interface VisitCountMapper {
    long countByExample(VisitCountExample example);

    int deleteByExample(VisitCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VisitCount record);

    int insertSelective(VisitCount record);

    List<VisitCount> selectByExample(VisitCountExample example);

    VisitCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VisitCount record, @Param("example") VisitCountExample example);

    int updateByExample(@Param("record") VisitCount record, @Param("example") VisitCountExample example);

    int updateByPrimaryKeySelective(VisitCount record);

    int updateByPrimaryKey(VisitCount record);
    
    Long getVisitCountTotal(GetVisitCountTotalDTO dto);
}