package demo.interaction.wechat.mapper;

import demo.interaction.wechat.pojo.po.WechatUserFromQrcode;
import demo.interaction.wechat.pojo.po.WechatUserFromQrcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatUserFromQrcodeMapper {
    long countByExample(WechatUserFromQrcodeExample example);

    int deleteByExample(WechatUserFromQrcodeExample example);

    int deleteByPrimaryKey(Long wechatUserId);

    int insert(WechatUserFromQrcode row);

    int insertSelective(WechatUserFromQrcode row);

    List<WechatUserFromQrcode> selectByExampleWithRowbounds(WechatUserFromQrcodeExample example, RowBounds rowBounds);

    List<WechatUserFromQrcode> selectByExample(WechatUserFromQrcodeExample example);

    WechatUserFromQrcode selectByPrimaryKey(Long wechatUserId);

    int updateByExampleSelective(@Param("row") WechatUserFromQrcode row, @Param("example") WechatUserFromQrcodeExample example);

    int updateByExample(@Param("row") WechatUserFromQrcode row, @Param("example") WechatUserFromQrcodeExample example);

    int updateByPrimaryKeySelective(WechatUserFromQrcode row);

    int updateByPrimaryKey(WechatUserFromQrcode row);
}