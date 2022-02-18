package demo.interaction.image.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.image.mapper.ImageCloudinaryMapper;
import demo.image.mapper.ImageComplexMapper;
import demo.image.mapper.ImageStoreMapper;
import demo.image.mapper.ImageTagMapper;
import demo.image.pojo.constant.ImageConstant;
import demo.image.pojo.dto.BatchUpdateDeleteFlagDTO;
import demo.image.pojo.dto.FindOldAutoTestImageOnCloudinaryDTO;
import demo.image.pojo.po.ImageCloudinary;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageStoreExample;
import demo.image.pojo.po.ImageTag;
import demo.image.pojo.type.ImageTagType;
import demo.interaction.image.service.ImageInteractionService;
import demo.thirdPartyAPI.cloudinary.service.CloudinaryService;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;
import toolPack.cloudinary.pojo.constant.CloudinaryConstant;
import toolPack.cloudinary.pojo.result.CloudinaryDeleteResult;
import toolPack.cloudinary.pojo.result.CloudinaryUploadResult;

@Service
public class ImageInteractionServiceImpl extends CommonService implements ImageInteractionService {

	@Autowired
	protected SystemOptionService systemConstantService;
	@Autowired
	private ImageStoreMapper imageStoreMapper;
	@Autowired
	private ImageTagMapper imageTagMapper;
	@Autowired
	private ImageCloudinaryMapper imageCloudinaryMapper;
	@Autowired
	private ImageComplexMapper imageComplexMapper;
	@Autowired
	private CloudinaryService cloudinaryService;

	@Override
	public UploadImageToCloudinaryResult uploadImageToCloudinary(UploadImageToCloudinaryDTO dto) {
		UploadImageToCloudinaryResult r = new UploadImageToCloudinaryResult();
		
		if("dev".equals(systemConstantService.getEnvName())) {
			return r;
		}
		
		if(StringUtils.isBlank(dto.getFilePath())) {
			r.failWithMessage("null param");
			return r;
		}
		
		File imgFile = new File(dto.getFilePath());
		if(!imgFile.exists()) {
			r.failWithMessage("file not exists");
			return r;
		}
		
		CloudinaryUploadResult uploadResult = cloudinaryService.uploadCore(imgFile);
		if(!uploadResult.isSuccess()) {
			r.failWithMessage("cloudinary upload fail");
			return r;
		}
		
		ImageStore imgPO = new ImageStore();
		Long imgId = snowFlake.getNextId();
		imgPO.setImageId(imgId);
		imgPO.setImageName(imgFile.getName());
		imgPO.setImageUrl(uploadResult.getSecureUrl());
		imageStoreMapper.insertSelective(imgPO);
		
		ImageCloudinary imgCloudPO = new ImageCloudinary();
		imgCloudPO.setImageId(imgId);
		imgCloudPO.setCloudinaryPublicId(uploadResult.getPublicId());
		imageCloudinaryMapper.insertSelective(imgCloudPO);
		
		ImageTag imgTagPO = new ImageTag();
		imgTagPO.setImageId(imgId);
		imgTagPO.setTagId(ImageTagType.autoTestImgToCloudinary.getCode().longValue());
		imageTagMapper.insertSelective(imgTagPO);
		
		r.setImgId(imgId);
		r.setImgUrl(uploadResult.getSecureUrl());
		r.setIsSuccess();
		
		return r;
	}
	
	@Override
	public void cleanOldAutoTestUploadImage() {
		
		LocalDateTime deadLine = LocalDateTime.now().minusDays(ImageConstant.autoTestUploadImageMaxLifeDays);
		
		FindOldAutoTestImageOnCloudinaryDTO findOldAutoTestImageOnCloudinaryDTO = new FindOldAutoTestImageOnCloudinaryDTO();
		findOldAutoTestImageOnCloudinaryDTO.setEndTime(deadLine);
		findOldAutoTestImageOnCloudinaryDTO.setTagId(ImageTagType.autoTestImgToCloudinary.getCode().longValue());
		List<ImageCloudinary> imgCloudinaryPOList = imageComplexMapper.findOldAutoTestImageOnCloudinary(findOldAutoTestImageOnCloudinaryDTO);
		
		if(imgCloudinaryPOList == null || imgCloudinaryPOList.size() < 1) {
			return;
		}
		
		List<String> sourceCloudinaryPublicIdList = new ArrayList<String>();
		Map<String, Long> cloudinaryPublicIdMapImgStoreId = new HashMap<String, Long>();
		
		for(ImageCloudinary po : imgCloudinaryPOList) {
			if(po == null) {
				continue;
			}
			sourceCloudinaryPublicIdList.add(po.getCloudinaryPublicId());
			cloudinaryPublicIdMapImgStoreId.put(po.getCloudinaryPublicId(), po.getImageId());
		}
		
		if(sourceCloudinaryPublicIdList.size() > 100) {
			int step = CloudinaryConstant.deleteIdListMaxSize;
			List<String> tmpList = null;
			for(int i = 0; i < sourceCloudinaryPublicIdList.size(); i = i + step) {
				tmpList = new ArrayList<String>();
				if(i + step > sourceCloudinaryPublicIdList.size()) {
					tmpList = sourceCloudinaryPublicIdList.subList(i, sourceCloudinaryPublicIdList.size());
				} else {
					tmpList = sourceCloudinaryPublicIdList.subList(i, i + step);
				}
				cleanOldAutoTestUploadImageSubHandle(tmpList, cloudinaryPublicIdMapImgStoreId);
			}
			
		} else {
			cleanOldAutoTestUploadImageSubHandle(sourceCloudinaryPublicIdList, cloudinaryPublicIdMapImgStoreId);
		}
	}
	
	private void cleanOldAutoTestUploadImageSubHandle(List<String> sourceCloudinaryPublicIdList, Map<String, Long> cloudinaryPublicIdMapImgStoreId) {
		CloudinaryDeleteResult cloudinaryDeleteResult = cloudinaryService.delete(sourceCloudinaryPublicIdList);
		
		@SuppressWarnings("unchecked")
		Set<String> keys = cloudinaryDeleteResult.getDeleted().keySet();
		String tmpValue = null;
		List<Long> targetImgStoreIdList = new ArrayList<Long>();
		for(String key : keys) {
			tmpValue = cloudinaryDeleteResult.getDeleted().getString(key);
			if("deleted".equals(tmpValue)) {
				targetImgStoreIdList.add(cloudinaryPublicIdMapImgStoreId.get(key));
			}
		}
		
		BatchUpdateDeleteFlagDTO imageCloudinaryBatchUpdateDeleteFlagDTO = new BatchUpdateDeleteFlagDTO();
		imageCloudinaryBatchUpdateDeleteFlagDTO.setIsDelete(true);
		imageCloudinaryBatchUpdateDeleteFlagDTO.setImageIdList(targetImgStoreIdList);
		imageCloudinaryMapper.batchUpdateDeleteFlag(imageCloudinaryBatchUpdateDeleteFlagDTO);
		
		ImageStoreExample iamgeStoreDeleteExample = new ImageStoreExample();
		iamgeStoreDeleteExample.createCriteria().andImageIdIn(targetImgStoreIdList);
		imageStoreMapper.deleteByExample(iamgeStoreDeleteExample);
	}
}
