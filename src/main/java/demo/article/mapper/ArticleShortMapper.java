package demo.article.mapper;

import demo.article.pojo.po.ArticleShort;

public interface ArticleShortMapper {
    int insert(ArticleShort record);

    int insertSelective(ArticleShort record);
}