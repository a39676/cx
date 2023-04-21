package demo.ai.aiChat.mapper;

import demo.ai.aiChat.pojo.po.AiChatUserChatHistory;
import demo.ai.aiChat.pojo.po.AiChatUserChatHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AiChatUserChatHistoryMapper {
    long countByExample(AiChatUserChatHistoryExample example);

    int deleteByExample(AiChatUserChatHistoryExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(AiChatUserChatHistory row);

    int insertSelective(AiChatUserChatHistory row);

    List<AiChatUserChatHistory> selectByExampleWithRowbounds(AiChatUserChatHistoryExample example, RowBounds rowBounds);

    List<AiChatUserChatHistory> selectByExample(AiChatUserChatHistoryExample example);

    AiChatUserChatHistory selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("row") AiChatUserChatHistory row, @Param("example") AiChatUserChatHistoryExample example);

    int updateByExample(@Param("row") AiChatUserChatHistory row, @Param("example") AiChatUserChatHistoryExample example);

    int updateByPrimaryKeySelective(AiChatUserChatHistory row);

    int updateByPrimaryKey(AiChatUserChatHistory row);
}