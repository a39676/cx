package demo.article.mapper;

import demo.article.pojo.po.ArticleLongComplaint;

public interface ArticleLongComplaintMapper {
    int insert(ArticleLongComplaint record);

    int insertSelective(ArticleLongComplaint record);
}