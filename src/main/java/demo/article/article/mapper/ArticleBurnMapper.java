package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleBurn;
import demo.article.article.pojo.result.ArticleBurnResult;
import demo.article.article.pojo.po.ArticleBurnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleBurnMapper {
    long countByExample(ArticleBurnExample example);

	int deleteByExample(ArticleBurnExample example);

	int deleteByPrimaryKey(Long articleId);

	List<ArticleBurn> selectByExample(ArticleBurnExample example);

	ArticleBurn selectByPrimaryKey(Long articleId);

	int updateByExampleSelective(@Param("record") ArticleBurn record, @Param("example") ArticleBurnExample example);

	int updateByExample(@Param("record") ArticleBurn record, @Param("example") ArticleBurnExample example);

	int updateByPrimaryKeySelective(ArticleBurn record);

	int updateByPrimaryKey(ArticleBurn record);

	int insert(ArticleBurn record);

    int insertSelective(ArticleBurn record);
    
    ArticleBurnResult findArticleByReadKey(Long readId);
    
    void burnArticleByBurnKey(Long burnId);
    
    void readCountPlus(Long readId);
    
    void lastRead(Long readId);
}