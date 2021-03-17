package demo.finance.metal.mapper;

import demo.finance.metal.pojo.po.MetalPriceNotice;
import demo.finance.metal.pojo.po.MetalPriceNoticeExample;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetalPriceNoticeMapper {
    long countByExample(MetalPriceNoticeExample example);

    int deleteByExample(MetalPriceNoticeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPriceNotice record);

    int insertSelective(MetalPriceNotice record);

    List<MetalPriceNotice> selectByExampleWithRowbounds(MetalPriceNoticeExample example, RowBounds rowBounds);

    List<MetalPriceNotice> selectByExample(MetalPriceNoticeExample example);

    MetalPriceNotice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPriceNotice record, @Param("example") MetalPriceNoticeExample example);

    int updateByExample(@Param("record") MetalPriceNotice record, @Param("example") MetalPriceNoticeExample example);

    int updateByPrimaryKeySelective(MetalPriceNotice record);

    int updateByPrimaryKey(MetalPriceNotice record);

	List<MetalPriceNotice> selectValidNoticeSetting(LocalDateTime now);
}