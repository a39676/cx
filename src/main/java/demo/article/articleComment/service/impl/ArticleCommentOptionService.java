package demo.article.articleComment.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class ArticleCommentOptionService extends CommonService {

	@Value("${optionFilePath.articleComment}")
	private String optionFilePath;

	private String articleCommentStorePrefixPath = null;
	private Long maxArticleCommentLength = 0L;
	private Short commentPageMaxSize = 30;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			ArticleCommentOptionService tmp = new Gson().fromJson(jsonStr, ArticleCommentOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("educate option loaded");
		} catch (Exception e) {
			log.error("article comment option loading error: " + e.getLocalizedMessage());
		}
	}

	@Override
	public String toString() {
		return "ArticleCommentConstantService [optionFilePath=" + optionFilePath + ", articleCommentStorePrefixPath="
				+ articleCommentStorePrefixPath + ", maxArticleCommentLength=" + maxArticleCommentLength
				+ ", commentPageMaxSize=" + commentPageMaxSize + "]";
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
