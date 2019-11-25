package demo.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.image.pojo.ImageCache;
import demo.image.pojo.ImageRemark;
import demo.image.pojo.po.ImageStore;

public interface ImageRemarkMapper {
    int insert(ImageRemark record);

	int insertSelective(ImageRemark record);
    
    void batchInsert(@Param("imageCaches")List<ImageCache> imageCaches);
    
    void insertOrUpdate(@Param("imageCache")ImageCache imageCache);
    
    List<String> findImageRemarkByEmptyImageId();
    
    void fillImageId(@Param("list")List<ImageStore> imageStores);
}