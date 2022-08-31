package jp.co.ginga.web.domain.service.stub;

import java.util.List;
import java.util.Map;

import jp.co.ginga.infra.repository.userrole.UserRoleEntity;
import jp.co.ginga.infra.repository.userrole.UserRoleRepository;

public class StubUserRoleRepository implements UserRoleRepository {

	@Override
	public List<UserRoleEntity> findAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Map<String, Object>> findRoleIdByUserId(String username) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<UserRoleEntity> findByUserId(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<UserRoleEntity> findByRoleId(String roleId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean insert(UserRoleEntity userRoleEntity) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int update(List<UserRoleEntity> afterUserRoleEntity, List<UserRoleEntity> beforeUserRoleEntity) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int delete(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public boolean updateDelete(UserRoleEntity userRoleEntity) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
