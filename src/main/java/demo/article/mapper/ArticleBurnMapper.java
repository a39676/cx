package demo.article.mapper;

import demo.article.pojo.po.ArticleBurn;
import demo.article.pojo.result.ArticleBurnResult;

public interface ArticleBurnMapper {
    int insert(ArticleBurn record);

    int insertSelective(ArticleBurn record);
    
    ArticleBurnResult findArticleByReadKey(String readKey);
    
    void burnArticleByBurnKey(String burnKey);
    
    void readCountPlus(String readKey);
    
    void lastRead(String readKey);
}