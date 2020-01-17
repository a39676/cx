package demo.article.article.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang.StringUtils;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleBurnMapper;
import demo.article.article.mapper.ArticleShortMapper;
import demo.article.article.pojo.constant.ArticleBurnUrlConstant;
import demo.article.article.pojo.constant.ArticleUrlConstant;
import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.article.article.pojo.po.ArticleBurn;
import demo.article.article.pojo.po.ArticleShort;
import demo.article.article.pojo.result.ArticleBurnResult;
import demo.article.article.pojo.result.CreatingBurnMessageResult;
import demo.article.article.service.ArticleBurnService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.pojo.type.ResultTypeCX;

@Service
public class ArticleBurnServiceImpl extends ArticleCommonService implements ArticleBurnService {

	@Autowired
	private ArticleShortMapper articleShortMapper;
	@Autowired
	private ArticleBurnMapper articleBurnMapper;
	
	@Override
	public CreatingBurnMessageResult creatingBurnMessage(CreatingBurnMessageDTO dto) {
		ArticleShort p = new ArticleShort();
		CreatingBurnMessageResult r = new CreatingBurnMessageResult();
		PolicyFactory filter = textFilter.getFilter();
		String contentAfterSanitize = filter.sanitize(dto.getContent());
		if (contentAfterSanitize.length() > Integer
				.parseInt(constantService.getValByName(SystemConstantStore.articleShortMaxLength))) {
			r.fillWithResult(ResultTypeCX.errorParam);
			return r;
		}
		p.setContent(contentAfterSanitize);
		Long userId = baseUtilCustom.getUserId();
		p.setUserId(userId);
		Long newArticleId = snowFlake.getNextId();
		p.setArticleId(newArticleId);
		articleShortMapper.insertSelective(p);

		ArticleBurn po = new ArticleBurn();
		po.setArticleId(newArticleId);
		Long readId = snowFlake.getNextId();
		Long burnId = snowFlake.getNextId();
		po.setReadKey(readId);
		po.setBurnKey(burnId);

		if (dto.getReadLimit() != null) {
			if (dto.getReadLimit() > 1) {
				po.setReadLimit(dto.getReadLimit());
			}
			if (dto.getReadLimit() > 10) { // 暂定最多阅读10次
				po.setReadLimit(10);
			}
		} else {
			po.setReadLimit(3);
		}

		LocalDateTime now = LocalDateTime.now();
		int validMinute = 30;
		if (dto.getValidTime() != null && dto.getValidTime() > 30 && dto.getValidTime() < 4320) {
			validMinute = dto.getValidTime();
		}
		po.setValidTime(now.plusMinutes(validMinute));
		articleBurnMapper.insertSelective(po);
		
		r.setReadKey(encryptId(po.getReadKey()));
		r.setBurnKey(encryptId(po.getBurnKey()));
		r.setReadUri(ArticleUrlConstant.root + ArticleBurnUrlConstant.readBurningMessage + "?readKey=" + r.getReadKey());
		r.setBurnUri(ArticleUrlConstant.root + ArticleBurnUrlConstant.burnMessage + "?burnKey=" + r.getBurnKey());
		r.setIsSuccess();
		
		return r;
	}

	@Override
	public ArticleBurnResult findArticleByReadKey(String readKey) {
		ArticleBurnResult p;
		if (StringUtils.isBlank(readKey)) {
			p = new ArticleBurnResult();
			p.setContent("信息已过期.");
			return p;
		}

		Long readKeyId = decryptPrivateKey(readKey);
		p = articleBurnMapper.findArticleByReadKey(readKeyId);

		if (p == null) {
			p = new ArticleBurnResult();
			p.setContent("信息已过期.");
			p.setReadCount(1);
			p.setReadLimit(1);
			return p;
		}

		if (p.getReadLimit() <= p.getReadCount()) {
			Long burnKeyId = decryptPrivateKey(p.getBurnKey());
			articleBurnMapper.burnArticleByBurnKey(burnKeyId);
			p.setContent("信息已过期.");
			return p;
		}

		if (p.getReadCount() < p.getReadLimit() - 1) {
			articleBurnMapper.readCountPlus(readKeyId);
			return p;
		} else if (p.getReadCount() == p.getReadLimit() - 1) {
			articleBurnMapper.lastRead(readKeyId);
			return p;
		}

		p = new ArticleBurnResult();
		p.setContent("信息已过期.");
		return p;
	}

	@Override
	public void burnArticleByBurnKey(String burnKey) {
		if (StringUtils.isBlank(burnKey)) {
			return;
		}
		
		Long burnId = decryptPrivateKey(burnKey);
		if(burnId != null) {
			articleBurnMapper.burnArticleByBurnKey(burnId);
		}
	}

}
