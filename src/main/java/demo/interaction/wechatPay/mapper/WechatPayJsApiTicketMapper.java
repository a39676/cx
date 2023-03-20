package demo.interaction.wechatPay.mapper;

import demo.interaction.wechatPay.pojo.po.WechatPayJsApiTicket;
import demo.interaction.wechatPay.pojo.po.WechatPayJsApiTicketExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatPayJsApiTicketMapper {
    long countByExample(WechatPayJsApiTicketExample example);

    int deleteByExample(WechatPayJsApiTicketExample example);

    int deleteByPrimaryKey(String merchantId);

    int insert(WechatPayJsApiTicket row);

    int insertSelective(WechatPayJsApiTicket row);

    List<WechatPayJsApiTicket> selectByExampleWithRowbounds(WechatPayJsApiTicketExample example, RowBounds rowBounds);

    List<WechatPayJsApiTicket> selectByExample(WechatPayJsApiTicketExample example);

    WechatPayJsApiTicket selectByPrimaryKey(String merchantId);

    int updateByExampleSelective(@Param("row") WechatPayJsApiTicket row, @Param("example") WechatPayJsApiTicketExample example);

    int updateByExample(@Param("row") WechatPayJsApiTicket row, @Param("example") WechatPayJsApiTicketExample example);

    int updateByPrimaryKeySelective(WechatPayJsApiTicket row);

    int updateByPrimaryKey(WechatPayJsApiTicket row);
}