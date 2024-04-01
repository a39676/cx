package demo.article.articleComment.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.costom_component.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class ArticleCommentOptionService extends CommonService {

	private String articleCommentStorePrefixPath = null;
	private Long maxArticleCommentLength = 0L;
	private Short commentPageMaxSize = 30;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.ARTICLE_COMMENT);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.ARTICLE_COMMENT);
			ArticleCommentOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("educate option loaded");
		} catch (Exception e) {
			log.error("article comment option loading error: " + e.getLocalizedMessage());
		}
	}

	public void setArticleCommentStorePrefixPath(String articleCommentStorePrefixPath) {
		this.articleCommentStorePrefixPath = articleCommentStorePrefixPath;
	}

	public void setMaxArticleCommentLength(Long maxArticleCommentLength) {
		this.maxArticleCommentLength = maxArticleCommentLength;
	}

	public void setCommentPageMaxSize(Short commentPageMaxSize) {
		this.commentPageMaxSize = commentPageMaxSize;
	}

	public String getArticleCommentStorePrefixPath() {
		return articleCommentStorePrefixPath;
	}

	public Long getMaxArticleCommentLength() {
		return maxArticleCommentLength;
	}

	public Short getCommentPageMaxSize() {
		return commentPageMaxSize;
	}

}
