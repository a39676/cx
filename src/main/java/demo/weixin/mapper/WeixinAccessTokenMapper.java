package demo.weixin.mapper;

import java.util.Date;
import java.util.List;

import demo.weixin.pojo.po.WeixinAccessToken;

public interface WeixinAccessTokenMapper {
    int insert(WeixinAccessToken record);

    int insertSelective(WeixinAccessToken record);
    
    String getNewToken();
    
    List<WeixinAccessToken> getTokens();
    
    int deleteOldToken(Date inputTime);
}