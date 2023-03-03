package demo.thirdPartyAPI.openAI.mapper;

import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserDetail;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OpenAiUserDetailMapper {
    long countByExample(OpenAiUserDetailExample example);

    int deleteByExample(OpenAiUserDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpenAiUserDetail row);

    int insertSelective(OpenAiUserDetail row);

    List<OpenAiUserDetail> selectByExampleWithRowbounds(OpenAiUserDetailExample example, RowBounds rowBounds);

    List<OpenAiUserDetail> selectByExample(OpenAiUserDetailExample example);

    OpenAiUserDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") OpenAiUserDetail row, @Param("example") OpenAiUserDetailExample example);

    int updateByExample(@Param("row") OpenAiUserDetail row, @Param("example") OpenAiUserDetailExample example);

    int updateByPrimaryKeySelective(OpenAiUserDetail row);

    int updateByPrimaryKey(OpenAiUserDetail row);
}