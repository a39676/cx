package demo.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.image.pojo.ImageCache;

public interface ImageCacheMapper {
	int insert(ImageCache record);

    int insertSelective(ImageCache record);
    
    List<ImageCache> getImageCaches();
    
    List<ImageCache> getImageCaches(@Param("limit")Integer limit);
    
    int updateDownloadMark(List<Long> imageIds);
    
    int deleteDownloadedCache(List<Long> imageIds);
    
    int batchInsert(List<ImageCache> images);
    
    List<ImageCache> findCacheByArticleId(Long articleId);
}