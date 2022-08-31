/**
 *
 */
package jp.co.ginga.web.domain.service.stub;

import java.util.List;

import jp.co.ginga.infra.repository.user.UserEntity;
import jp.co.ginga.infra.repository.user.UserRepository;

/**
 * @author shuheitakaoka
 *
 */
public class StubUserRepository implements UserRepository {

	@Override
	public List<UserEntity> findAll() {
		return null;
	}

	@Override
	public UserEntity findByUserId(String userId) {
		return null;
	}

	@Override
	public List<UserEntity> getUserIdByUserId(String userId) {
		return null;
	}

	@Override
	public int insert(UserEntity entity) {
		return 0;
	}

	@Override
	public int update(UserEntity entity) {
		return 0;
	}

	@Override
	public int delete(String userId) {
		return 0;
	}

}
