package demo.article.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.mapper.ArticleViewCountMapper;
import demo.article.pojo.po.ArticleViewCount;
import demo.article.pojo.po.example.ArticleViewCountExample;
import demo.article.pojo.po.example.ArticleViewCountExample.Criteria;
import demo.article.service.ArticleViewService;
import demo.baseCommon.service.CommonService;

@Service
public class ArticleViewServiceImpl extends CommonService implements ArticleViewService {

	@Autowired
	private ArticleViewCountMapper articleViewCountMapper;
	
	@Override
	public List<ArticleViewCount> findArticleViewCountByArticleId(List<Long> articleIdList) {
		if(articleIdList == null || articleIdList.size() < 1) {
			return new ArrayList<ArticleViewCount>();
		}
		
		ArticleViewCountExample example = new ArticleViewCountExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdIn(articleIdList);
		return articleViewCountMapper.selectByExample(example);
	}
	
	@Override
	public ArticleViewCount findArticleViewCountByArticleId(Long articleId) {
		if(articleId == null) {
			return new ArticleViewCount();
		}
		
		ArticleViewCountExample example = new ArticleViewCountExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(articleId);
		return articleViewCountMapper.selectByExample(example).get(0);
	}
	
	@Override
	public int insertOrUpdateViewCount(Long articleId) {
		if(articleId == null) {
			return 0;
		}
		return articleViewCountMapper.insertOrUpdateViewCount(articleId);
	}
}
