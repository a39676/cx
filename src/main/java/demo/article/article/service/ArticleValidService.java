package demo.article.article.service;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleValidService {

	void addOrUpdateValid(Long articleId, LocalDateTime validDateTime);

	List<Long> getExpiredId();

	void cleanOldData();

	LocalDateTime getById(Long articleId);

}
