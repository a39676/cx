package demo.interaction.sms.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.interaction.sms.pojo.dto.QuerySendingFrequencyDTO;
import demo.interaction.sms.pojo.po.SmsSendingHistory;
import demo.interaction.sms.pojo.po.SmsSendingHistoryExample;
import demo.interaction.sms.pojo.result.QuerySendingFrequencyResult;

public interface SmsSendingHistoryMapper {
	long countByExample(SmsSendingHistoryExample example);

	int deleteByExample(SmsSendingHistoryExample example);

	int deleteByPrimaryKey(Long id);

	int insert(SmsSendingHistory row);

	int insertSelective(SmsSendingHistory row);

	List<SmsSendingHistory> selectByExampleWithRowbounds(SmsSendingHistoryExample example, RowBounds rowBounds);

	List<SmsSendingHistory> selectByExample(SmsSendingHistoryExample example);

	SmsSendingHistory selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("row") SmsSendingHistory row,
			@Param("example") SmsSendingHistoryExample example);

	int updateByExample(@Param("row") SmsSendingHistory row, @Param("example") SmsSendingHistoryExample example);

	int updateByPrimaryKeySelective(SmsSendingHistory row);

	int updateByPrimaryKey(SmsSendingHistory row);

	QuerySendingFrequencyResult querySendingFrequency(QuerySendingFrequencyDTO dto);

	int querySendedCounting(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}