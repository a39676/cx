package demo.interaction.wechat.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.interaction.wechat.pojo.po.WechatAccessToken;
import demo.interaction.wechat.pojo.po.WechatAccessTokenExample;

public interface WechatAccessTokenMapper {
    long countByExample(WechatAccessTokenExample example);

    int deleteByExample(WechatAccessTokenExample example);

    int deleteByPrimaryKey(String appId);

    int insert(WechatAccessToken row);

    int insertSelective(WechatAccessToken row);

    List<WechatAccessToken> selectByExampleWithRowbounds(WechatAccessTokenExample example, RowBounds rowBounds);

    List<WechatAccessToken> selectByExample(WechatAccessTokenExample example);

    WechatAccessToken selectByPrimaryKey(String appId);

    int updateByExampleSelective(@Param("row") WechatAccessToken row, @Param("example") WechatAccessTokenExample example);

    int updateByExample(@Param("row") WechatAccessToken row, @Param("example") WechatAccessTokenExample example);

    int updateByPrimaryKeySelective(WechatAccessToken row);

    int updateByPrimaryKey(WechatAccessToken row);
}