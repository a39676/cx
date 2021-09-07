package demo.automationTest.mapper;

import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.pojo.po.TestEventExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TestEventMapper {
    long countByExample(TestEventExample example);

    int deleteByExample(TestEventExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TestEvent record);

    int insertSelective(TestEvent record);

    List<TestEvent> selectByExampleWithRowbounds(TestEventExample example, RowBounds rowBounds);

    List<TestEvent> selectByExample(TestEventExample example);

    TestEvent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TestEvent record, @Param("example") TestEventExample example);

    int updateByExample(@Param("record") TestEvent record, @Param("example") TestEventExample example);

    int updateByPrimaryKeySelective(TestEvent record);

    int updateByPrimaryKey(TestEvent record);
}