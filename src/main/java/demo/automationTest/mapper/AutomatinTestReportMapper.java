package demo.automationTest.mapper;

import demo.automationTest.pojo.po.AutomatinTestReport;
import demo.automationTest.pojo.po.AutomatinTestReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AutomatinTestReportMapper {
    long countByExample(AutomatinTestReportExample example);

    int deleteByExample(AutomatinTestReportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AutomatinTestReport record);

    int insertSelective(AutomatinTestReport record);

    List<AutomatinTestReport> selectByExampleWithRowbounds(AutomatinTestReportExample example, RowBounds rowBounds);

    List<AutomatinTestReport> selectByExample(AutomatinTestReportExample example);

    AutomatinTestReport selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AutomatinTestReport record, @Param("example") AutomatinTestReportExample example);

    int updateByExample(@Param("record") AutomatinTestReport record, @Param("example") AutomatinTestReportExample example);

    int updateByPrimaryKeySelective(AutomatinTestReport record);

    int updateByPrimaryKey(AutomatinTestReport record);
}