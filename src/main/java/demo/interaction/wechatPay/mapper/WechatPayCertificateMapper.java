package demo.interaction.wechatPay.mapper;

import demo.interaction.wechatPay.pojo.po.WechatPayCertificate;
import demo.interaction.wechatPay.pojo.po.WechatPayCertificateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatPayCertificateMapper {
    long countByExample(WechatPayCertificateExample example);

    int deleteByExample(WechatPayCertificateExample example);

    int deleteByPrimaryKey(String merchantId);

    int insert(WechatPayCertificate row);

    int insertSelective(WechatPayCertificate row);

    List<WechatPayCertificate> selectByExampleWithRowbounds(WechatPayCertificateExample example, RowBounds rowBounds);

    List<WechatPayCertificate> selectByExample(WechatPayCertificateExample example);

    WechatPayCertificate selectByPrimaryKey(String merchantId);

    int updateByExampleSelective(@Param("row") WechatPayCertificate row, @Param("example") WechatPayCertificateExample example);

    int updateByExample(@Param("row") WechatPayCertificate row, @Param("example") WechatPayCertificateExample example);

    int updateByPrimaryKeySelective(WechatPayCertificate row);

    int updateByPrimaryKey(WechatPayCertificate row);
}