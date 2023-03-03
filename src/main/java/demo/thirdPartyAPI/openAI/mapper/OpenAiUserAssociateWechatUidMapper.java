package demo.thirdPartyAPI.openAI.mapper;

import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserAssociateWechatUidExample;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserAssociateWechatUidKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OpenAiUserAssociateWechatUidMapper {
    long countByExample(OpenAiUserAssociateWechatUidExample example);

    int deleteByExample(OpenAiUserAssociateWechatUidExample example);

    int deleteByPrimaryKey(OpenAiUserAssociateWechatUidKey key);

    int insert(OpenAiUserAssociateWechatUidKey row);

    int insertSelective(OpenAiUserAssociateWechatUidKey row);

    List<OpenAiUserAssociateWechatUidKey> selectByExampleWithRowbounds(OpenAiUserAssociateWechatUidExample example, RowBounds rowBounds);

    List<OpenAiUserAssociateWechatUidKey> selectByExample(OpenAiUserAssociateWechatUidExample example);

    int updateByExampleSelective(@Param("row") OpenAiUserAssociateWechatUidKey row, @Param("example") OpenAiUserAssociateWechatUidExample example);

    int updateByExample(@Param("row") OpenAiUserAssociateWechatUidKey row, @Param("example") OpenAiUserAssociateWechatUidExample example);
}