package demo.article.article.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.article.article.pojo.po.ArticleBurn;
import demo.article.article.pojo.po.ArticleBurnExample;

public interface ArticleBurnMapper {
	long countByExample(ArticleBurnExample example);

	int deleteByExample(ArticleBurnExample example);

	int deleteByPrimaryKey(Long articleId);

	int insert(ArticleBurn record);

	int insertSelective(ArticleBurn record);

	List<ArticleBurn> selectByExampleWithRowbounds(ArticleBurnExample example, RowBounds rowBounds);

	List<ArticleBurn> selectByExample(ArticleBurnExample example);

	ArticleBurn selectByPrimaryKey(Long articleId);

	int updateByExampleSelective(@Param("record") ArticleBurn record, @Param("example") ArticleBurnExample example);

	int updateByExample(@Param("record") ArticleBurn record, @Param("example") ArticleBurnExample example);

	int updateByPrimaryKeySelective(ArticleBurn record);

	int updateByPrimaryKey(ArticleBurn record);
	
	List<ArticleBurn> findExpiredArticleBurn(LocalDateTime validTime);

	int batchDeleteById(@Param("idList") List<Long> idList);
}