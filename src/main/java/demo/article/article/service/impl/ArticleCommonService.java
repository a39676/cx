package demo.article.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.config.costom_component.EncryptUtil;

public class ArticleCommonService extends CommonService{
	
	@Autowired
	private SystemConstantService systemConstantService;

	protected static String imageHttpUrlPattern;
	private static final String[] imageSuffix = { "jpg", "jpeg", "bmp", "tiff", "png", "gif", "webp" };
	
	private static List<List<Character>> customKey = null;
	
	static {{
		String imageSuffixPattern = "";
		for (int i = 0; i < imageSuffix.length; i++) {
			imageSuffixPattern = imageSuffixPattern + "(" + imageSuffix[i] + ")";
			if (i < imageSuffix.length - 1) {
				imageSuffixPattern = imageSuffixPattern + "|";
			}
		}

		imageHttpUrlPattern = "(https?://(?:.*)(/\\S+\\." + imageSuffixPattern + "))";
	}}
	
	public List<List<Character>> getCustomKey() {
		if(customKey == null) {
			loadCustomKey();
		}
		return customKey;
	}
	
	public boolean loadCustomKey() {
		if(customKey == null) {
			customKey = systemConstantService.getCustomKeys();
		}
		if (customKey.size() == 10) {
			for(List<Character> l : customKey) {
				if(l.size() != 10) {
					customKey = null;
					return false;
				}
			}
			return true;
		} else {
			customKey = null;
			return false;
		}
	}
	
	public String encryptArticleId(Long articleId, List<List<Character>> keys) {
		if(articleId == null) {
			return null;
		}
		
		if(keys == null || keys.size() < 1) {
			return null;
		}
		return EncryptUtil.customEncrypt(keys, articleId.toString());
	}
	
	public Long decryptArticlePrivateKey(String inputPk) {
		String encryptId = EncryptUtil.customDecrypt(getCustomKey(), inputPk);
		if(encryptId == null || !numberUtil.matchInteger(encryptId)) {
			return null;
		}
		return Long.parseLong(encryptId);
	}
}
