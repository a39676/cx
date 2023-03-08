package demo.aiChat.mapper;

import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AiChatUserAssociateWechatUidMapper {
    long countByExample(AiChatUserAssociateWechatUidExample example);

    int deleteByExample(AiChatUserAssociateWechatUidExample example);

    int deleteByPrimaryKey(AiChatUserAssociateWechatUidKey key);

    int insert(AiChatUserAssociateWechatUidKey row);

    int insertSelective(AiChatUserAssociateWechatUidKey row);

    List<AiChatUserAssociateWechatUidKey> selectByExampleWithRowbounds(AiChatUserAssociateWechatUidExample example, RowBounds rowBounds);

    List<AiChatUserAssociateWechatUidKey> selectByExample(AiChatUserAssociateWechatUidExample example);

    int updateByExampleSelective(@Param("row") AiChatUserAssociateWechatUidKey row, @Param("example") AiChatUserAssociateWechatUidExample example);

    int updateByExample(@Param("row") AiChatUserAssociateWechatUidKey row, @Param("example") AiChatUserAssociateWechatUidExample example);
}