package demo.ai.aiArt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecordExample;

public interface AiArtTextToImageJobRecordMapper {
	long countByExample(AiArtTextToImageJobRecordExample example);

	int deleteByExample(AiArtTextToImageJobRecordExample example);

	int deleteByPrimaryKey(Long id);

	int insert(AiArtTextToImageJobRecord row);

	int insertSelective(AiArtTextToImageJobRecord row);

	List<AiArtTextToImageJobRecord> selectByExampleWithRowbounds(AiArtTextToImageJobRecordExample example,
			RowBounds rowBounds);

	List<AiArtTextToImageJobRecord> selectByExample(AiArtTextToImageJobRecordExample example);

	AiArtTextToImageJobRecord selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("row") AiArtTextToImageJobRecord row,
			@Param("example") AiArtTextToImageJobRecordExample example);

	int updateByExample(@Param("row") AiArtTextToImageJobRecord row,
			@Param("example") AiArtTextToImageJobRecordExample example);

	int updateByPrimaryKeySelective(AiArtTextToImageJobRecord row);

	int updateByPrimaryKey(AiArtTextToImageJobRecord row);

	List<AiArtTextToImageJobRecord> findWaitingJobs(@Param("runCount") Integer runCount);
}