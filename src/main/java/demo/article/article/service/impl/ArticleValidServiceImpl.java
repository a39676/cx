package demo.article.article.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleValidMapper;
import demo.article.article.pojo.po.ArticleValid;
import demo.article.article.pojo.po.ArticleValidExample;
import demo.article.article.service.ArticleAdminService;
import demo.article.article.service.ArticleValidService;

@Service
public class ArticleValidServiceImpl extends ArticleCommonService implements ArticleValidService {

	@Autowired
	private ArticleAdminService articleAdminService;
	@Autowired
	private ArticleValidMapper validMapper;

	@Override
	public void addOrUpdateValid(Long articleId, LocalDateTime validDateTime) {
		if (articleId == null || validDateTime == null) {
			return;
		}

		if (validDateTime.isBefore(LocalDateTime.now())) {
			try {
				articleAdminService.deleteArticle(systemOptionService.encryptId(articleId));
			} catch (Exception e) {
				log.error("Delete article error, articleId: " + articleId + ", error: " + e.getLocalizedMessage());
			}
		}

		ArticleValid po = validMapper.selectByPrimaryKey(articleId);
		if (po == null) {
			po = new ArticleValid();
			po.setArticleId(articleId);
			po.setValidTime(validDateTime);
			validMapper.insertSelective(po);
		} else {
			po.setValidTime(validDateTime);
			validMapper.updateByPrimaryKeySelective(po);
		}
	}

	@Override
	public List<Long> getExpiredId() {
		ArticleValidExample example = new ArticleValidExample();
		example.createCriteria().andValidTimeLessThan(LocalDateTime.now());
		List<ArticleValid> poList = validMapper.selectByExample(example);
		if (poList == null || poList.isEmpty()) {
			return new ArrayList<>();
		}

		List<Long> result = new ArrayList<>();
		for (ArticleValid po : poList) {
			result.add(po.getArticleId());
		}

		return result;
	}

	@Override
	public void cleanOldData() {
		ArticleValidExample example = new ArticleValidExample();
		example.createCriteria().andValidTimeLessThan(LocalDateTime.now().minusDays(3));
		validMapper.deleteByExample(example);
	}

	@Override
	public LocalDateTime getById(Long articleId) {
		ArticleValid po = validMapper.selectByPrimaryKey(articleId);
		if(po != null) {
			return po.getValidTime();
		} else {
			return null;
		}
	}
}
