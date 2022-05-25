package demo.tool.calendarNotice.mapper;

import demo.tool.calendarNotice.pojo.po.CalendarPreNotice;
import demo.tool.calendarNotice.pojo.po.CalendarPreNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CalendarPreNoticeMapper {
    long countByExample(CalendarPreNoticeExample example);

    int deleteByExample(CalendarPreNoticeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CalendarPreNotice record);

    int insertSelective(CalendarPreNotice record);

    List<CalendarPreNotice> selectByExampleWithRowbounds(CalendarPreNoticeExample example, RowBounds rowBounds);

    List<CalendarPreNotice> selectByExample(CalendarPreNoticeExample example);

    CalendarPreNotice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CalendarPreNotice record, @Param("example") CalendarPreNoticeExample example);

    int updateByExample(@Param("record") CalendarPreNotice record, @Param("example") CalendarPreNoticeExample example);

    int updateByPrimaryKeySelective(CalendarPreNotice record);

    int updateByPrimaryKey(CalendarPreNotice record);
}