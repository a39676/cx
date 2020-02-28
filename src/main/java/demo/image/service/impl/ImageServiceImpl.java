package demo.image.service.impl;

import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.service.CommonService;
import demo.image.mapper.ImageStoreMapper;
import demo.image.mapper.ImageTagMapper;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageTag;
import demo.image.pojo.type.ImageTagType;
import demo.image.service.ImageService;
import image.pojo.constant.ImageSavingConstant;
import image.pojo.dto.ImageSavingDTO;

@Service
public class ImageServiceImpl extends CommonService implements ImageService {

	@Autowired
	private ImageStoreMapper imgMapper;
	@Autowired
	private ImageTagMapper imageTagMapper;
	
	@Override
	public CommonResult imageSaving(ImageSavingDTO dto) {
		CommonResult r = new CommonResult();
		if(dto == null 
				|| StringUtils.isAnyBlank(dto.getImgPath(), dto.getImgName()) 
				|| !dto.getImgPath().startsWith(ImageSavingConstant.imgSavingPath)
				|| !dto.getImgPath().endsWith(dto.getImgName())
				) {
			r.failWithMessage("error data");
			return r;
		}
		
		try {
			Paths.get(dto.getImgPath());
		} catch (Exception e) {
			r.failWithMessage("error data");
			return r;
		}
		
		ImageStore imgPO = new ImageStore();
		Long newImgId = snowFlake.getNextId();
		imgPO.setImageId(newImgId);
		imgPO.setImageUrl(dto.getImgPath());
		imgPO.setImageName(dto.getImgName());
		int insertCount = imgMapper.insertSelective(imgPO);
		if(insertCount < 1) {
			r.failWithMessage("service error");
			return r;
		}
		
		ImageTag imgTagPO = new ImageTag();
		imgTagPO.setImageId(newImgId);
		imgTagPO.setTagId(ImageTagType.imageSaving.getCode().longValue());
		insertCount = imageTagMapper.insertSelective(imgTagPO);
		if(insertCount < 1) {
			r.failWithMessage("service error");
			return r;
		}
		
		r.setIsSuccess();
		return r;
	}
}
