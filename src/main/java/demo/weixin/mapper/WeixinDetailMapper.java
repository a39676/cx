package demo.weixin.mapper;

import demo.weixin.pojo.po.WeixinDetail;

public interface WeixinDetailMapper {
    int insert(WeixinDetail record);

    int insertSelective(WeixinDetail record);
}