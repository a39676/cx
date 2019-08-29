package demo.fakePost.service;

import demo.base.user.pojo.type.RolesType;

public interface FakePostService {

	int createFakeEvaluationStore();

	/**
	 * -查找具有DELAY_POSTER权限的用户的N篇随机文章, 处理为通过
	 *  {@link RolesType}
	 * @throws Exception
	 */
	void autoPass() throws Exception;

}
