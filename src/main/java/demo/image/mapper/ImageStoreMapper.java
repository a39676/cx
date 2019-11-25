package demo.image.mapper;

import demo.image.pojo.po.ImageStore;
import demo.image.pojo.po.ImageStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageStoreMapper {

	long countByExample(ImageStoreExample example);

    int deleteByExample(ImageStoreExample example);

    int deleteByPrimaryKey(Long imageId);

    int insert(ImageStore record);

    int insertSelective(ImageStore record);

    List<ImageStore> selectByExample(ImageStoreExample example);

    ImageStore selectByPrimaryKey(Long imageId);

    int updateByExampleSelective(@Param("record") ImageStore record, @Param("example") ImageStoreExample example);

    int updateByExample(@Param("record") ImageStore record, @Param("example") ImageStoreExample example);

    int updateByPrimaryKeySelective(ImageStore record);

    int updateByPrimaryKey(ImageStore record);
    
    Integer checkImageExistsByMD5(String md5);
    
    int batchInsert(List<ImageStore> imageStoreList);
    
    List<ImageStore> findImageIdByMd5Mark(@Param("md5s")List<String> md5s);
}