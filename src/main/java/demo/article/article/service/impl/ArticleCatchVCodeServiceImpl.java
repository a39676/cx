package demo.article.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleSummaryVCodeMapper;
import demo.article.article.pojo.po.ArticleSummaryVCode;
import demo.article.article.service.ArticleCatchVCodeService;
import demo.toyParts.vcode.pojo.param.type.VCodeType;
import demo.toyParts.vcode.pojo.po.VCode;

@Service
public class ArticleCatchVCodeServiceImpl implements ArticleCatchVCodeService {

	@Autowired
	private ArticleSummaryVCodeMapper articleSummaryVCodeMapper;
	
	private ArticleSummaryVCode findArticleSummaryInfo(Long vcodeId) {
		if(vcodeId == null) {
			return null;
		}
		return articleSummaryVCodeMapper.findByVCodeId(vcodeId);
	}
	
	@Override
	public ArticleSummaryVCode findArticleSummaryInfo(VCode vcode) {
		if(vcode != null 
				&& vcode.getIsDelete() == false 
				&& vcode.getValidTime().getTime() > System.currentTimeMillis() 
				&& vcode.getCodeType() != null 
				&& VCodeType.forHR.equals(VCodeType.getType(vcode.getCodeType()))) {
			return findArticleSummaryInfo(vcode.getCodeId());
		} else {
			return findArticleSummaryInfo(1000L);
		}
	}
	
}
