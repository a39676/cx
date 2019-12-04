package demo.toyParts.woqu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.toyParts.woqu.pojo.po.Wusers;
import demo.toyParts.woqu.pojo.po.WusersExample;

public interface WusersMapper {
    long countByExample(WusersExample example);

    int deleteByExample(WusersExample example);

    int deleteByPrimaryKey(Long mobile);

    int insert(Wusers record);

    int insertSelective(Wusers record);

    List<Wusers> selectByExample(WusersExample example);

    Wusers selectByPrimaryKey(Long mobile);

    int updateByExampleSelective(@Param("record") Wusers record, @Param("example") WusersExample example);

    int updateByExample(@Param("record") Wusers record, @Param("example") WusersExample example);

    int updateByPrimaryKeySelective(Wusers record);

    int updateByPrimaryKey(Wusers record);
    
    int insertSelectiveIgnore(Wusers wuser);
}