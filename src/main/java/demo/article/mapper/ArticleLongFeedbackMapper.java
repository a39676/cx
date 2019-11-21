package demo.article.mapper;

import demo.article.pojo.po.ArticleLongFeedback;
import demo.article.pojo.po.ArticleLongFeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleLongFeedbackMapper {
    long countByExample(ArticleLongFeedbackExample example);

    int deleteByExample(ArticleLongFeedbackExample example);

    int insert(ArticleLongFeedback record);

    int insertSelective(ArticleLongFeedback record);

    List<ArticleLongFeedback> selectByExample(ArticleLongFeedbackExample example);

    int updateByExampleSelective(@Param("record") ArticleLongFeedback record, @Param("example") ArticleLongFeedbackExample example);

    int updateByExample(@Param("record") ArticleLongFeedback record, @Param("example") ArticleLongFeedbackExample example);
}