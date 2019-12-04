package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleShort;

public interface ArticleShortMapper {
    int insert(ArticleShort record);

    int insertSelective(ArticleShort record);
}