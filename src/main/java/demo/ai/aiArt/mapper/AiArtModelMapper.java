package demo.ai.aiArt.mapper;

import demo.ai.aiArt.pojo.po.AiArtModel;
import demo.ai.aiArt.pojo.po.AiArtModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AiArtModelMapper {
    long countByExample(AiArtModelExample example);

    int deleteByExample(AiArtModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AiArtModel row);

    int insertSelective(AiArtModel row);

    List<AiArtModel> selectByExampleWithRowbounds(AiArtModelExample example, RowBounds rowBounds);

    List<AiArtModel> selectByExample(AiArtModelExample example);

    AiArtModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") AiArtModel row, @Param("example") AiArtModelExample example);

    int updateByExample(@Param("row") AiArtModel row, @Param("example") AiArtModelExample example);

    int updateByPrimaryKeySelective(AiArtModel row);

    int updateByPrimaryKey(AiArtModel row);
}