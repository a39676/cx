package demo.ai.aiArt.mapper;

import demo.ai.aiArt.pojo.po.AiArtGeneratingRecord;
import demo.ai.aiArt.pojo.po.AiArtGeneratingRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AiArtGeneratingRecordMapper {
    long countByExample(AiArtGeneratingRecordExample example);

    int deleteByExample(AiArtGeneratingRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AiArtGeneratingRecord row);

    int insertSelective(AiArtGeneratingRecord row);

    List<AiArtGeneratingRecord> selectByExampleWithRowbounds(AiArtGeneratingRecordExample example, RowBounds rowBounds);

    List<AiArtGeneratingRecord> selectByExample(AiArtGeneratingRecordExample example);

    AiArtGeneratingRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AiArtGeneratingRecord row, @Param("example") AiArtGeneratingRecordExample example);

    int updateByExample(@Param("row") AiArtGeneratingRecord row, @Param("example") AiArtGeneratingRecordExample example);

    int updateByPrimaryKeySelective(AiArtGeneratingRecord row);

    int updateByPrimaryKey(AiArtGeneratingRecord row);
}