package demo.joy.image.icon.service.impl;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.image.pojo.result.ImgHandleSrcDataResult;
import demo.image.service.ImageService;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.service.JoyCommonService;
import demo.joy.image.icon.mapper.JoyImageIconMapper;
import demo.joy.image.icon.pojo.constant.IconConstant;
import demo.joy.image.icon.pojo.dto.BatchUploadIconDTO;
import demo.joy.image.icon.service.JoyIconService;

@Service
public class JoyIconServiceImpl extends JoyCommonService implements JoyIconService {

	@SuppressWarnings("unused")
	@Autowired
	private JoyImageIconMapper iconMapper;
	
	@Autowired
	private ImageService imgService;
	
	public JoyCommonResult batchUploadIcon(BatchUploadIconDTO dto) {
		/*
		 * TODO
		 * upload 同时加入 redis??
		 */
		JoyCommonResult r = new JoyCommonResult();
		
		if(StringUtils.isAnyBlank(dto.getContent(), dto.getRemark())) {
			return r;
		}
		
		Element doc = Jsoup.parse(dto.getContent());
        Elements imgs = doc.select("img[src]");
        /* 解决如果文章内有本地上传的图片, 转到服务器硬盘保存, 并提供 url 访问, */
        for(Element s : imgs) {
        	imgBase64ToIconStore(s.attr("src"));
        }
        
//		TODO
		return r;
	}
	
	private void imgBase64ToIconStore(String src) {
//		TODO
		ImgHandleSrcDataResult srcHandleResult = imgService.imgHandleSrcData(src);
		if(srcHandleResult.isFail()) {
//			TODO
		}
		
		String filename = String.valueOf(snowFlake.getNextId()) + "." + srcHandleResult.getImgFileType();
		
		BufferedImage bufferedImage = imgService.base64ToBufferedImg(srcHandleResult.getBase64Str());
		if(bufferedImage == null) {
//			TODO
		}
		
		String saveingFolderPath = null;
		if(isLinux()) {
			saveingFolderPath = IconConstant.ICON_LOCAL_STORE ;
		} else if(isWindows()) {
			saveingFolderPath = "d:/" + IconConstant.ICON_LOCAL_STORE;
		}
		String imgSavingPath = saveingFolderPath + "/" + filename;
		boolean saveFlag = imgService.imgSaveAsFile(bufferedImage, imgSavingPath, srcHandleResult.getImgFileType());
		if(!saveFlag) {
//			TODO
		}
		
	}
}
