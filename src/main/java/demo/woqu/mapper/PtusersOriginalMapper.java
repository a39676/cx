package demo.woqu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.woqu.pojo.po.PtusersOriginal;
import demo.woqu.pojo.po.PtusersOriginalExample;

public interface PtusersOriginalMapper {
    long countByExample(PtusersOriginalExample example);

    int deleteByExample(PtusersOriginalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PtusersOriginal record);

    int insertSelective(PtusersOriginal record);

    List<PtusersOriginal> selectByExample(PtusersOriginalExample example);

    PtusersOriginal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PtusersOriginal record, @Param("example") PtusersOriginalExample example);

    int updateByExample(@Param("record") PtusersOriginal record, @Param("example") PtusersOriginalExample example);

    int updateByPrimaryKeySelective(PtusersOriginal record);

    int updateByPrimaryKey(PtusersOriginal record);
    
    List<PtusersOriginal> selectAllGroupByMobile();
}