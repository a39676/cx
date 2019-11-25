package demo.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.image.pojo.ImageTag;
import demo.image.pojo.po.ImageStore;

public interface ImageTagMapper {
    int insert(ImageTag record);

	int insertSelective(ImageTag record);

	int batchInsert(List<ImageTag> imageTagList);
	
	List<String> findImageTagByEmptyImageId();
	
	void fillImageId(@Param("list")List<ImageStore> imageStores);
	
}