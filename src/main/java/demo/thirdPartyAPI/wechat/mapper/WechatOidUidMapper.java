package demo.thirdPartyAPI.wechat.mapper;

import demo.thirdPartyAPI.wechat.pojo.po.WechatOidUid;
import demo.thirdPartyAPI.wechat.pojo.po.WechatOidUidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatOidUidMapper {
    long countByExample(WechatOidUidExample example);

    int deleteByExample(WechatOidUidExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatOidUid row);

    int insertSelective(WechatOidUid row);

    List<WechatOidUid> selectByExampleWithRowbounds(WechatOidUidExample example, RowBounds rowBounds);

    List<WechatOidUid> selectByExample(WechatOidUidExample example);

    WechatOidUid selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") WechatOidUid row, @Param("example") WechatOidUidExample example);

    int updateByExample(@Param("row") WechatOidUid row, @Param("example") WechatOidUidExample example);

    int updateByPrimaryKeySelective(WechatOidUid row);

    int updateByPrimaryKey(WechatOidUid row);
}