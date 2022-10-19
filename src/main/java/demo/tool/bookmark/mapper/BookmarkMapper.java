package demo.tool.bookmark.mapper;

import demo.tool.bookmark.pojo.po.Bookmark;
import demo.tool.bookmark.pojo.po.BookmarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BookmarkMapper {
    long countByExample(BookmarkExample example);

    int deleteByExample(BookmarkExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Bookmark row);

    int insertSelective(Bookmark row);

    List<Bookmark> selectByExampleWithRowbounds(BookmarkExample example, RowBounds rowBounds);

    List<Bookmark> selectByExample(BookmarkExample example);

    Bookmark selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") Bookmark row, @Param("example") BookmarkExample example);

    int updateByExample(@Param("row") Bookmark row, @Param("example") BookmarkExample example);

    int updateByPrimaryKeySelective(Bookmark row);

    int updateByPrimaryKey(Bookmark row);
}