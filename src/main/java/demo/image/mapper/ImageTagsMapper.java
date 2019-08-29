package demo.image.mapper;

import java.util.List;

import demo.image.pojo.ImageTags;

public interface ImageTagsMapper {
    int insert(ImageTags record);

	int insertSelective(ImageTags record);

	List<ImageTags> getTagList();
}