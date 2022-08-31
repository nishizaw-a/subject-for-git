package jp.co.ginga.web.domain.service.stub;

import java.util.List;

import jp.co.ginga.infra.repository.role.RoleEntity;
import jp.co.ginga.infra.repository.role.RoleRepository;

public class StubRoleRepository implements RoleRepository {

	@Override
	public List<RoleEntity> findAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public RoleEntity findByRoleId(String roleId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int insert(RoleEntity roleEntity) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int update(RoleEntity roleEntity) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int delete(String roleEntity) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
