package demo.tool.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.tool.pojo.po.MailRecord;
import demo.tool.pojo.po.MailRecordExample;

public interface MailRecordMapper {
    long countByExample(MailRecordExample example);

	int deleteByExample(MailRecordExample example);

	int deleteByPrimaryKey(Long mailId);

	List<MailRecord> selectByExample(MailRecordExample example);

	MailRecord selectByPrimaryKey(Long mailId);

	int updateByExampleSelective(@Param("record") MailRecord record, @Param("example") MailRecordExample example);

	int updateByExample(@Param("record") MailRecord record, @Param("example") MailRecordExample example);

	int updateByPrimaryKeySelective(MailRecord record);

	int updateByPrimaryKey(MailRecord record);

	int insert(MailRecord record);

    int insertSelective(MailRecord record);
    
    int cleanMailRecord(@Param("validTime")LocalDateTime validTime);
    
    int hasMailTask();
}