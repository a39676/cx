package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleBurn;
import demo.article.article.pojo.result.ArticleBurnResult;

public interface ArticleBurnMapper {
    int insert(ArticleBurn record);

    int insertSelective(ArticleBurn record);
    
    ArticleBurnResult findArticleByReadKey(String readKey);
    
    void burnArticleByBurnKey(String burnKey);
    
    void readCountPlus(String readKey);
    
    void lastRead(String readKey);
}