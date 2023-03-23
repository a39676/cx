package demo.aiChat.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.pojo.po.AiChatUserDetailExample;

public interface AiChatUserDetailMapper {
	long countByExample(AiChatUserDetailExample example);

	int deleteByExample(AiChatUserDetailExample example);

	int deleteByPrimaryKey(Long id);

	int insert(AiChatUserDetail row);

	int insertSelective(AiChatUserDetail row);

	List<AiChatUserDetail> selectByExampleWithRowbounds(AiChatUserDetailExample example, RowBounds rowBounds);

	List<AiChatUserDetail> selectByExample(AiChatUserDetailExample example);

	AiChatUserDetail selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("row") AiChatUserDetail row, @Param("example") AiChatUserDetailExample example);

	int updateByExample(@Param("row") AiChatUserDetail row, @Param("example") AiChatUserDetailExample example);

	int updateByPrimaryKeySelective(AiChatUserDetail row);

	int updateByPrimaryKey(AiChatUserDetail row);

	int batchRecharge(@Param("aiChatUserIdList") List<Long> aiChatUserIdList,
			@Param("bonusAmount") BigDecimal bonusAmount, @Param("rechargeAmount") BigDecimal rechargeAmount);
}