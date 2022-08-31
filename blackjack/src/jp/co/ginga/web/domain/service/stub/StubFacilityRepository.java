package jp.co.ginga.web.domain.service.stub;

import java.util.List;

import jp.co.ginga.infra.repository.facility.FacilityEntity;
import jp.co.ginga.infra.repository.facility.FacilityRepository;

public class StubFacilityRepository implements FacilityRepository {
	@Override
	public List<FacilityEntity> findAll() {
		return null;
	}

	@Override
	public FacilityEntity findByFacilityId(int facilityId) {
		return null;
	}

	@Override
	public List<FacilityEntity> findByFacilityTypeId(int facilityTypeId) {
		return null;
	}

	@Override
	public List<FacilityEntity> findByName(String name) {
		return null;
	}

	@Override
	public int insert(FacilityEntity facilityEntity) {
		return 0;
	}

	@Override
	public int update(FacilityEntity facilityEntity) {
		return 0;
	}

	@Override
	public int delete(int facilityId) {
		return 0;
	}
}
