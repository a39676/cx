package demo.thirdPartyAPI.wechat.mapper;

import demo.thirdPartyAPI.wechat.pojo.po.WechatUserDetail;
import demo.thirdPartyAPI.wechat.pojo.po.WechatUserDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatUserDetailMapper {
    long countByExample(WechatUserDetailExample example);

    int deleteByExample(WechatUserDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatUserDetail row);

    int insertSelective(WechatUserDetail row);

    List<WechatUserDetail> selectByExampleWithRowbounds(WechatUserDetailExample example, RowBounds rowBounds);

    List<WechatUserDetail> selectByExample(WechatUserDetailExample example);

    WechatUserDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") WechatUserDetail row, @Param("example") WechatUserDetailExample example);

    int updateByExample(@Param("row") WechatUserDetail row, @Param("example") WechatUserDetailExample example);

    int updateByPrimaryKeySelective(WechatUserDetail row);

    int updateByPrimaryKey(WechatUserDetail row);
}