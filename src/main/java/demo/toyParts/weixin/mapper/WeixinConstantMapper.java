package demo.toyParts.weixin.mapper;

import demo.toyParts.weixin.pojo.po.WeixinConstant;

public interface WeixinConstantMapper {
    int insert(WeixinConstant record);

    int insertSelective(WeixinConstant record);
    
    String getConstant(String constantName);
    
    int updateConstant(String constantValue);
}