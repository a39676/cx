package demo.tool.taobao.mapper;

import demo.tool.taobao.pojo.po.TaobaoProductSource;
import demo.tool.taobao.pojo.po.TaobaoProductSourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TaobaoProductSourceMapper {
    long countByExample(TaobaoProductSourceExample example);

    int deleteByExample(TaobaoProductSourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaobaoProductSource row);

    int insertSelective(TaobaoProductSource row);

    List<TaobaoProductSource> selectByExampleWithRowbounds(TaobaoProductSourceExample example, RowBounds rowBounds);

    List<TaobaoProductSource> selectByExample(TaobaoProductSourceExample example);

    TaobaoProductSource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TaobaoProductSource row, @Param("example") TaobaoProductSourceExample example);

    int updateByExample(@Param("row") TaobaoProductSource row, @Param("example") TaobaoProductSourceExample example);

    int updateByPrimaryKeySelective(TaobaoProductSource row);

    int updateByPrimaryKey(TaobaoProductSource row);
}