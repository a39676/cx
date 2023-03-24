package demo.interaction.wechatPay.mapper;

import demo.interaction.wechatPay.pojo.po.WechatPayJsApiHistory;
import demo.interaction.wechatPay.pojo.po.WechatPayJsApiHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatPayJsApiHistoryMapper {
    long countByExample(WechatPayJsApiHistoryExample example);

    int deleteByExample(WechatPayJsApiHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatPayJsApiHistory row);

    int insertSelective(WechatPayJsApiHistory row);

    List<WechatPayJsApiHistory> selectByExampleWithRowbounds(WechatPayJsApiHistoryExample example, RowBounds rowBounds);

    List<WechatPayJsApiHistory> selectByExample(WechatPayJsApiHistoryExample example);

    WechatPayJsApiHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") WechatPayJsApiHistory row, @Param("example") WechatPayJsApiHistoryExample example);

    int updateByExample(@Param("row") WechatPayJsApiHistory row, @Param("example") WechatPayJsApiHistoryExample example);

    int updateByPrimaryKeySelective(WechatPayJsApiHistory row);

    int updateByPrimaryKey(WechatPayJsApiHistory row);
}