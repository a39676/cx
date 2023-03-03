package demo.thirdPartyAPI.openAI.mapper;

import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserAmountHistory;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserAmountHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OpenAiUserAmountHistoryMapper {
    long countByExample(OpenAiUserAmountHistoryExample example);

    int deleteByExample(OpenAiUserAmountHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpenAiUserAmountHistory row);

    int insertSelective(OpenAiUserAmountHistory row);

    List<OpenAiUserAmountHistory> selectByExampleWithRowbounds(OpenAiUserAmountHistoryExample example, RowBounds rowBounds);

    List<OpenAiUserAmountHistory> selectByExample(OpenAiUserAmountHistoryExample example);

    OpenAiUserAmountHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") OpenAiUserAmountHistory row, @Param("example") OpenAiUserAmountHistoryExample example);

    int updateByExample(@Param("row") OpenAiUserAmountHistory row, @Param("example") OpenAiUserAmountHistoryExample example);

    int updateByPrimaryKeySelective(OpenAiUserAmountHistory row);

    int updateByPrimaryKey(OpenAiUserAmountHistory row);
}