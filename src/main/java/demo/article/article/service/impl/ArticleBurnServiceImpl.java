package demo.article.article.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleBurnMapper;
import demo.article.article.mapper.ArticleShortMapper;
import demo.article.article.pojo.po.ArticleBurn;
import demo.article.article.pojo.po.ArticleShort;
import demo.article.article.pojo.result.ArticleBurnResult;
import demo.article.article.pojo.result.CreatingBurnMessageResult;
import demo.article.article.service.ArticleBurnService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.baseCommon.service.CommonService;
import demo.config.costom_component.BaseUtilCustom;
import net.sf.json.JSONObject;

@Service
public class ArticleBurnServiceImpl extends CommonService implements ArticleBurnService {

	@Autowired
	private SystemConstantService systemConstantService;
	
	@Autowired
	private ArticleShortMapper articleShortMapper;
	@Autowired
	private ArticleBurnMapper articleBurnMapper;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@Override
	public CreatingBurnMessageResult creatingBurnMessage(JSONObject jsonInput) {
		ArticleShort p = new ArticleShort();
		CreatingBurnMessageResult result = new CreatingBurnMessageResult();
		if (!jsonInput.containsKey("content")) {
			p.setContent("");
		}
		String contentAfterEscapeHtml = StringEscapeUtils.escapeHtml(jsonInput.getString("content"));
		if (contentAfterEscapeHtml.length() > Integer
				.parseInt(systemConstantService.getValByName(SystemConstantStore.articleShortMaxLength))) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		p.setContent(contentAfterEscapeHtml);
		Long userId = baseUtilCustom.getUserId();
		p.setUserId(userId);
		Long newArticleId = snowFlake.getNextId();
		p.setArticleId(newArticleId);
		articleShortMapper.insertSelective(p);

		ArticleBurn pb = new ArticleBurn();
		pb.setArticleId(newArticleId);
		pb.setBurnKey(UUID.randomUUID().toString().replaceAll("-", ""));
		pb.setReadKey(UUID.randomUUID().toString().replaceAll("-", ""));

		if (numberUtil.matchInteger(jsonInput.getString("readLimit"))) {
			if (jsonInput.getInt("readLimit") > 1) {
				pb.setReadLimit(jsonInput.getInt("readLimit"));
			}
			if (jsonInput.getInt("readLimit") > 10) { // 暂定最多阅读10次
				pb.setReadLimit(10);
			}
		} else {
			pb.setReadLimit(3);
		}

		LocalDateTime now = LocalDateTime.now();
		int validMinute = 30;
		if (jsonInput.containsKey("validTime") && numberUtil.matchInteger(jsonInput.getString("validTime")) && jsonInput.getInt("validTime") > 30
				&& jsonInput.getInt("validTime") < 4320) {
			validMinute = jsonInput.getInt("validTime");
		}
		pb.setValidTime(dateHandler.localDateTimeToDate(now.plusMinutes(validMinute)));
		articleBurnMapper.insertSelective(pb);
		result.normalSuccess();
		return result;
	}

	@Override
	public ArticleBurnResult findArticleByReadKey(String readKey) {
		ArticleBurnResult p;
		if (StringUtils.isBlank(readKey)) {
			p = new ArticleBurnResult();
			p.setContent("信息已过期.");
			return p;
		}

		p = articleBurnMapper.findArticleByReadKey(readKey);

		if (p == null) {
			p = new ArticleBurnResult();
			p.setContent("信息已过期.");
			p.setReadCount(1);
			p.setReadLimit(1);
			return p;
		}

		if (p.getReadLimit() <= p.getReadCount()) {
			articleBurnMapper.burnArticleByBurnKey(p.getBurnKey());
			p.setContent("信息已过期.");
			return p;
		}

		if (p.getReadCount() < p.getReadLimit() - 1) {
			articleBurnMapper.readCountPlus(p.getReadKey());
			return p;
		} else if (p.getReadCount() == p.getReadLimit() - 1) {
			articleBurnMapper.lastRead(p.getReadKey());
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
		articleBurnMapper.burnArticleByBurnKey(burnKey);
	}

}
