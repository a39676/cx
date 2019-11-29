package demo.image.mapper;

import java.util.List;

import demo.image.pojo.dto.FindOldAutoTestImageOnCloudinaryDTO;
import demo.image.pojo.po.ImageCloudinary;

public interface ImageComplexMapper {

	List<ImageCloudinary> findOldAutoTestImageOnCloudinary(FindOldAutoTestImageOnCloudinaryDTO dto);
	
}
