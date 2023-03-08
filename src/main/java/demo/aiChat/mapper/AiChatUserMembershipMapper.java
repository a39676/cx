package demo.aiChat.mapper;

import demo.aiChat.pojo.po.AiChatUserMembership;
import demo.aiChat.pojo.po.AiChatUserMembershipExample;
import demo.aiChat.pojo.po.AiChatUserMembershipKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AiChatUserMembershipMapper {
    long countByExample(AiChatUserMembershipExample example);

    int deleteByExample(AiChatUserMembershipExample example);

    int deleteByPrimaryKey(AiChatUserMembershipKey key);

    int insert(AiChatUserMembership row);

    int insertSelective(AiChatUserMembership row);

    List<AiChatUserMembership> selectByExampleWithRowbounds(AiChatUserMembershipExample example, RowBounds rowBounds);

    List<AiChatUserMembership> selectByExample(AiChatUserMembershipExample example);

    AiChatUserMembership selectByPrimaryKey(AiChatUserMembershipKey key);

    int updateByExampleSelective(@Param("row") AiChatUserMembership row, @Param("example") AiChatUserMembershipExample example);

    int updateByExample(@Param("row") AiChatUserMembership row, @Param("example") AiChatUserMembershipExample example);

    int updateByPrimaryKeySelective(AiChatUserMembership row);

    int updateByPrimaryKey(AiChatUserMembership row);
}