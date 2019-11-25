package demo.image.mapper;

import demo.image.pojo.po.ImageCloudinary;
import demo.image.pojo.po.ImageCloudinaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageCloudinaryMapper {
    long countByExample(ImageCloudinaryExample example);

    int deleteByExample(ImageCloudinaryExample example);

    int deleteByPrimaryKey(Long imageId);

    int insert(ImageCloudinary record);

    int insertSelective(ImageCloudinary record);

    List<ImageCloudinary> selectByExample(ImageCloudinaryExample example);

    ImageCloudinary selectByPrimaryKey(Long imageId);

    int updateByExampleSelective(@Param("record") ImageCloudinary record, @Param("example") ImageCloudinaryExample example);

    int updateByExample(@Param("record") ImageCloudinary record, @Param("example") ImageCloudinaryExample example);

    int updateByPrimaryKeySelective(ImageCloudinary record);

    int updateByPrimaryKey(ImageCloudinary record);
}