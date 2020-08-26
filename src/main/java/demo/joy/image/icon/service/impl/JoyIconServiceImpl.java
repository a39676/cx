package demo.joy.image.icon.service.impl;

import java.awt.image.BufferedImage;
import java.util.List;

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
import demo.joy.image.icon.pojo.po.JoyImageIcon;
import demo.joy.image.icon.pojo.po.JoyImageIconExample;
import demo.joy.image.icon.service.JoyIconService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class JoyIconServiceImpl extends JoyCommonService implements JoyIconService {

	@Autowired
	private JoyImageIconMapper iconMapper;
	
	@Autowired
	private ImageService imgService;
	
	public JoyCommonResult batchUploadIcon(BatchUploadIconDTO dto) {
		JoyCommonResult r = new JoyCommonResult();
		
		if(StringUtils.isAnyBlank(dto.getContent(), dto.getRemark())) {
			return r;
		}
		
		Element doc = Jsoup.parse(dto.getContent());
        Elements imgs = doc.select("img[src]");
        for(Element s : imgs) {
        	imgBase64ToIconStore(s.attr("src"), dto.getRemark());
        }
        
		return r;
	}
	
	private void imgBase64ToIconStore(String src, String remark) {
		ImgHandleSrcDataResult srcHandleResult = imgService.imgHandleSrcData(src);
		if(srcHandleResult.isFail()) {
			return;
		}
		
		BufferedImage bufferedImage = imgService.base64ToBufferedImg(srcHandleResult.getBase64Str());
		if(bufferedImage == null) {
			return;
		}
		
		String imgSavingPath = saveBufferedImgAsFile(bufferedImage, srcHandleResult.getImgFileType());
		if(imgSavingPath == null) {
			return;
		}
		
		JoyImageIcon po = new JoyImageIcon();
		po.setId(snowFlake.getNextId());
		po.setImgPath(imgSavingPath);
		po.setRemark(remark);
		
		if(iconMapper.insertSelective(po) > 0) {
			loadToRedis(po);
		}
		
	}
	
	private String saveBufferedImgAsFile(BufferedImage bufferedImage, String fileType) {
		String filename = String.valueOf(snowFlake.getNextId()) + "." + fileType;
		String saveingFolderPath = null;
		if(isLinux()) {
			saveingFolderPath = IconConstant.ICON_LOCAL_STORE ;
		} else if(isWindows()) {
			saveingFolderPath = "d:/" + IconConstant.ICON_LOCAL_STORE;
		}
		String imgSavingPath = saveingFolderPath + "/" + filename;
		
		if(imgService.imgSaveAsFile(bufferedImage, imgSavingPath, fileType)) {
			return imgSavingPath;
		} else {
			return null;
		}
	}

	private void loadToRedis(JoyImageIcon po) {
		FileUtilCustom ioUtil = new FileUtilCustom();
		byte[] imageByte = null;
		try {
			imageByte = ioUtil.getByteFromFile(po.getImgPath());
			redisByteTemplate.opsForValue().set(IconConstant.ICON_REDIS_PREFIX + String.valueOf(po.getId()), imageByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] getIcon(Long id) {
		if(id == null) {
			return null;
		}
		
		String keyName = IconConstant.ICON_REDIS_PREFIX + String.valueOf(id);
		
		if(redisByteTemplate.hasKey(keyName)) {
			return redisByteTemplate.opsForValue().get(keyName);
		} else {
			JoyImageIcon po = iconMapper.selectByPrimaryKey(id);
			if(po == null || StringUtils.isBlank(po.getImgPath())) {
				return null;
			}
			loadToRedis(po);
			
			FileUtilCustom ioUtil = new FileUtilCustom();
			
			try {
				return ioUtil.getByteFromFile(po.getImgPath());
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	@Override
	public void loadAllIconToRedis() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		byte[] imageByte = null;
		
		JoyImageIconExample example = new JoyImageIconExample();
		example.createCriteria();
		List<JoyImageIcon> poList = iconMapper.selectByExample(example);
		for(JoyImageIcon po : poList) {
			try {
				imageByte = ioUtil.getByteFromFile(po.getImgPath());
				redisByteTemplate.opsForValue().set(IconConstant.ICON_REDIS_PREFIX + String.valueOf(po.getId()), imageByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
