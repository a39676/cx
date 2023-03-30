package demo.interaction.wechat.mapper;

import demo.interaction.wechat.pojo.po.WechatQrcodeDetail;
import demo.interaction.wechat.pojo.po.WechatQrcodeDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatQrcodeDetailMapper {
    long countByExample(WechatQrcodeDetailExample example);

    int deleteByExample(WechatQrcodeDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatQrcodeDetail row);

    int insertSelective(WechatQrcodeDetail row);

    List<WechatQrcodeDetail> selectByExampleWithRowbounds(WechatQrcodeDetailExample example, RowBounds rowBounds);

    List<WechatQrcodeDetail> selectByExample(WechatQrcodeDetailExample example);

    WechatQrcodeDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") WechatQrcodeDetail row, @Param("example") WechatQrcodeDetailExample example);

    int updateByExample(@Param("row") WechatQrcodeDetail row, @Param("example") WechatQrcodeDetailExample example);

    int updateByPrimaryKeySelective(WechatQrcodeDetail row);

    int updateByPrimaryKey(WechatQrcodeDetail row);
}