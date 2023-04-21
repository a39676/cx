package demo.ai.aiChat.mapper;

import demo.ai.aiChat.pojo.po.SystemUserAssociateAiChatUserExample;
import demo.ai.aiChat.pojo.po.SystemUserAssociateAiChatUserKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SystemUserAssociateAiChatUserMapper {
    long countByExample(SystemUserAssociateAiChatUserExample example);

    int deleteByExample(SystemUserAssociateAiChatUserExample example);

    int deleteByPrimaryKey(SystemUserAssociateAiChatUserKey key);

    int insert(SystemUserAssociateAiChatUserKey row);

    int insertSelective(SystemUserAssociateAiChatUserKey row);

    List<SystemUserAssociateAiChatUserKey> selectByExampleWithRowbounds(SystemUserAssociateAiChatUserExample example, RowBounds rowBounds);

    List<SystemUserAssociateAiChatUserKey> selectByExample(SystemUserAssociateAiChatUserExample example);

    int updateByExampleSelective(@Param("row") SystemUserAssociateAiChatUserKey row, @Param("example") SystemUserAssociateAiChatUserExample example);

    int updateByExample(@Param("row") SystemUserAssociateAiChatUserKey row, @Param("example") SystemUserAssociateAiChatUserExample example);
}