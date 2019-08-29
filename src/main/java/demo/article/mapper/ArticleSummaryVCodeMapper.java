package demo.article.mapper;

import demo.article.pojo.po.ArticleSummaryVCode;

public interface ArticleSummaryVCodeMapper {
    int insert(ArticleSummaryVCode record);

    int insertSelective(ArticleSummaryVCode record);
    
    ArticleSummaryVCode findByVCodeId(Long vcodeId);
}