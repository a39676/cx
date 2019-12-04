package demo.toyParts.weixin.mapper;

import demo.toyParts.weixin.pojo.po.WeixinDetail;

public interface WeixinDetailMapper {
    int insert(WeixinDetail record);

    int insertSelective(WeixinDetail record);
}