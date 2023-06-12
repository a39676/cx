package demo.promote.mapper;

import demo.promote.pojo.po.PromoteVisitHistory;
import demo.promote.pojo.po.PromoteVisitHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PromoteVisitHistoryMapper {
    long countByExample(PromoteVisitHistoryExample example);

    int deleteByExample(PromoteVisitHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PromoteVisitHistory row);

    int insertSelective(PromoteVisitHistory row);

    List<PromoteVisitHistory> selectByExampleWithRowbounds(PromoteVisitHistoryExample example, RowBounds rowBounds);

    List<PromoteVisitHistory> selectByExample(PromoteVisitHistoryExample example);

    PromoteVisitHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") PromoteVisitHistory row, @Param("example") PromoteVisitHistoryExample example);

    int updateByExample(@Param("row") PromoteVisitHistory row, @Param("example") PromoteVisitHistoryExample example);

    int updateByPrimaryKeySelective(PromoteVisitHistory row);

    int updateByPrimaryKey(PromoteVisitHistory row);
}