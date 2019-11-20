package demo.article.mapper;

import demo.article.pojo.po.ArticleLongComplaint;
import demo.article.pojo.po.ArticleLongComplaintExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleLongComplaintMapper {
    long countByExample(ArticleLongComplaintExample example);

	int deleteByExample(ArticleLongComplaintExample example);

	List<ArticleLongComplaint> selectByExample(ArticleLongComplaintExample example);

	int updateByExampleSelective(@Param("record") ArticleLongComplaint record,
			@Param("example") ArticleLongComplaintExample example);

	int updateByExample(@Param("record") ArticleLongComplaint record,
			@Param("example") ArticleLongComplaintExample example);

	int insert(ArticleLongComplaint record);

    int insertSelective(ArticleLongComplaint record);
}