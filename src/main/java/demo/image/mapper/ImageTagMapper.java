package demo.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.image.pojo.ImageStore;
import demo.image.pojo.ImageTag;

public interface ImageTagMapper {
    int insert(ImageTag record);

	int insertSelective(ImageTag record);

	int batchInsert(List<ImageTag> imageTagList);
	
	List<String> findImageTagByEmptyImageId();
	
	void fillImageId(@Param("list")List<ImageStore> imageStores);
	
}