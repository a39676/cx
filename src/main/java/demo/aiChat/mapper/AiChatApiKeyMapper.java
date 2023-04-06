package demo.aiChat.mapper;

import demo.aiChat.pojo.po.AiChatApiKey;
import demo.aiChat.pojo.po.AiChatApiKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AiChatApiKeyMapper {
    long countByExample(AiChatApiKeyExample example);

    int deleteByExample(AiChatApiKeyExample example);

    int deleteByPrimaryKey(Long apiKeyDecrypt);

    int insert(AiChatApiKey row);

    int insertSelective(AiChatApiKey row);

    List<AiChatApiKey> selectByExampleWithRowbounds(AiChatApiKeyExample example, RowBounds rowBounds);

    List<AiChatApiKey> selectByExample(AiChatApiKeyExample example);

    AiChatApiKey selectByPrimaryKey(Long apiKeyDecrypt);

    int updateByExampleSelective(@Param("row") AiChatApiKey row, @Param("example") AiChatApiKeyExample example);

    int updateByExample(@Param("row") AiChatApiKey row, @Param("example") AiChatApiKeyExample example);

    int updateByPrimaryKeySelective(AiChatApiKey row);

    int updateByPrimaryKey(AiChatApiKey row);
}