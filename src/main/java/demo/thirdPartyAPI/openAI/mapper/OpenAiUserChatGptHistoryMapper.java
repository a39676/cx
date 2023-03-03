package demo.thirdPartyAPI.openAI.mapper;

import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserChatGptHistory;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserChatGptHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OpenAiUserChatGptHistoryMapper {
    long countByExample(OpenAiUserChatGptHistoryExample example);

    int deleteByExample(OpenAiUserChatGptHistoryExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(OpenAiUserChatGptHistory row);

    int insertSelective(OpenAiUserChatGptHistory row);

    List<OpenAiUserChatGptHistory> selectByExampleWithRowbounds(OpenAiUserChatGptHistoryExample example, RowBounds rowBounds);

    List<OpenAiUserChatGptHistory> selectByExample(OpenAiUserChatGptHistoryExample example);

    OpenAiUserChatGptHistory selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("row") OpenAiUserChatGptHistory row, @Param("example") OpenAiUserChatGptHistoryExample example);

    int updateByExample(@Param("row") OpenAiUserChatGptHistory row, @Param("example") OpenAiUserChatGptHistoryExample example);

    int updateByPrimaryKeySelective(OpenAiUserChatGptHistory row);

    int updateByPrimaryKey(OpenAiUserChatGptHistory row);
}