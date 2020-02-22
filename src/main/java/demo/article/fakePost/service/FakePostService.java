package demo.article.fakePost.service;

import demo.base.user.pojo.type.SystemRolesType;

public interface FakePostService {

	int createFakeEvaluationStore();

	/**
	 * -查找具有DELAY_POSTER权限的用户的N篇随机文章, 处理为通过
	 *  {@link SystemRolesType}
	 * @throws Exception
	 */
	void autoPass() throws Exception;

}
