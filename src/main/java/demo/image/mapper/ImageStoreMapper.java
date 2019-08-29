package demo.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.image.pojo.ImageStore;

public interface ImageStoreMapper {
    int insert(ImageStore record);

    int insertSelective(ImageStore record);
    
    Integer checkImageExistsByMD5(String md5);
    
    int batchInsert(List<ImageStore> imageStoreList);
    
    List<ImageStore> findImageIdByMd5Mark(@Param("md5s")List<String> md5s);
}