package demo.tool.telegram.mapper;

import demo.tool.telegram.pojo.po.TelegramConstant;
import demo.tool.telegram.pojo.po.TelegramConstantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TelegramConstantMapper {
    long countByExample(TelegramConstantExample example);

    int deleteByExample(TelegramConstantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TelegramConstant record);

    int insertSelective(TelegramConstant record);

    List<TelegramConstant> selectByExampleWithRowbounds(TelegramConstantExample example, RowBounds rowBounds);

    List<TelegramConstant> selectByExample(TelegramConstantExample example);

    TelegramConstant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TelegramConstant record, @Param("example") TelegramConstantExample example);

    int updateByExample(@Param("record") TelegramConstant record, @Param("example") TelegramConstantExample example);

    int updateByPrimaryKeySelective(TelegramConstant record);

    int updateByPrimaryKey(TelegramConstant record);
}