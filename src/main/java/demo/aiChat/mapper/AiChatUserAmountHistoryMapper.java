package demo.aiChat.mapper;

import demo.aiChat.pojo.po.AiChatUserAmountHistory;
import demo.aiChat.pojo.po.AiChatUserAmountHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AiChatUserAmountHistoryMapper {
    long countByExample(AiChatUserAmountHistoryExample example);

    int deleteByExample(AiChatUserAmountHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AiChatUserAmountHistory row);

    int insertSelective(AiChatUserAmountHistory row);

    List<AiChatUserAmountHistory> selectByExampleWithRowbounds(AiChatUserAmountHistoryExample example, RowBounds rowBounds);

    List<AiChatUserAmountHistory> selectByExample(AiChatUserAmountHistoryExample example);

    AiChatUserAmountHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AiChatUserAmountHistory row, @Param("example") AiChatUserAmountHistoryExample example);

    int updateByExample(@Param("row") AiChatUserAmountHistory row, @Param("example") AiChatUserAmountHistoryExample example);

    int updateByPrimaryKeySelective(AiChatUserAmountHistory row);

    int updateByPrimaryKey(AiChatUserAmountHistory row);
}