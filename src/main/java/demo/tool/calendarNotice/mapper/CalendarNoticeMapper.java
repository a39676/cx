package demo.tool.calendarNotice.mapper;

import demo.tool.calendarNotice.pojo.po.CalendarNotice;
import demo.tool.calendarNotice.pojo.po.CalendarNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CalendarNoticeMapper {
    long countByExample(CalendarNoticeExample example);

    int deleteByExample(CalendarNoticeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CalendarNotice record);

    int insertSelective(CalendarNotice record);

    List<CalendarNotice> selectByExampleWithRowbounds(CalendarNoticeExample example, RowBounds rowBounds);

    List<CalendarNotice> selectByExample(CalendarNoticeExample example);

    CalendarNotice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CalendarNotice record, @Param("example") CalendarNoticeExample example);

    int updateByExample(@Param("record") CalendarNotice record, @Param("example") CalendarNoticeExample example);

    int updateByPrimaryKeySelective(CalendarNotice record);

    int updateByPrimaryKey(CalendarNotice record);
}