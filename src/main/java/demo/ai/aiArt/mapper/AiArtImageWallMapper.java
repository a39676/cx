package demo.ai.aiArt.mapper;

import demo.ai.aiArt.pojo.po.AiArtImageWall;
import demo.ai.aiArt.pojo.po.AiArtImageWallExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AiArtImageWallMapper {
    long countByExample(AiArtImageWallExample example);

    int deleteByExample(AiArtImageWallExample example);

    int deleteByPrimaryKey(Long imgId);

    int insert(AiArtImageWall row);

    int insertSelective(AiArtImageWall row);

    List<AiArtImageWall> selectByExampleWithRowbounds(AiArtImageWallExample example, RowBounds rowBounds);

    List<AiArtImageWall> selectByExample(AiArtImageWallExample example);

    AiArtImageWall selectByPrimaryKey(Long imgId);

    int updateByExampleSelective(@Param("row") AiArtImageWall row, @Param("example") AiArtImageWallExample example);

    int updateByExample(@Param("row") AiArtImageWall row, @Param("example") AiArtImageWallExample example);

    int updateByPrimaryKeySelective(AiArtImageWall row);

    int updateByPrimaryKey(AiArtImageWall row);
}