package demo.tool.textMessageForward.telegram.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.tool.textMessageForward.telegram.pojo.po.TelegramChatId;
import demo.tool.textMessageForward.telegram.pojo.po.TelegramChatIdExample;

public interface TelegramChatIdMapper {
    long countByExample(TelegramChatIdExample example);

    int deleteByExample(TelegramChatIdExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TelegramChatId record);

    int insertSelective(TelegramChatId record);

    List<TelegramChatId> selectByExampleWithRowbounds(TelegramChatIdExample example, RowBounds rowBounds);

    List<TelegramChatId> selectByExample(TelegramChatIdExample example);

    TelegramChatId selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TelegramChatId record, @Param("example") TelegramChatIdExample example);

    int updateByExample(@Param("record") TelegramChatId record, @Param("example") TelegramChatIdExample example);

    int updateByPrimaryKeySelective(TelegramChatId record);

    int updateByPrimaryKey(TelegramChatId record);
}