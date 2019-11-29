package demo.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageTag;
import demo.image.pojo.po.ImageTagExample;

public interface ImageTagMapper {

	long countByExample(ImageTagExample example);

	int deleteByExample(ImageTagExample example);

	int insert(ImageTag record);

	int insertSelective(ImageTag record);

	List<ImageTag> selectByExample(ImageTagExample example);

	int updateByExampleSelective(@Param("record") ImageTag record, @Param("example") ImageTagExample example);

	int updateByExample(@Param("record") ImageTag record, @Param("example") ImageTagExample example);

	int batchInsert(List<ImageTag> imageTagList);
	
	List<String> findImageTagByEmptyImageId();
	
	void fillImageId(@Param("list")List<ImageStore> imageStores);
	
	List<ImageTag> findByTagId(@Param("tagId") Long tagId);
	
}