package demo.article.article.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.article.article.pojo.po.ArticleLongFeedback;
import demo.article.article.pojo.po.ArticleLongFeedbackExample;

public interface ArticleLongFeedbackMapper {
    long countByExample(ArticleLongFeedbackExample example);

    int deleteByExample(ArticleLongFeedbackExample example);

    int insert(ArticleLongFeedback record);

    int insertSelective(ArticleLongFeedback record);

    List<ArticleLongFeedback> selectByExample(ArticleLongFeedbackExample example);

    int updateByExampleSelective(@Param("record") ArticleLongFeedback record, @Param("example") ArticleLongFeedbackExample example);

    int updateByExample(@Param("record") ArticleLongFeedback record, @Param("example") ArticleLongFeedbackExample example);
}