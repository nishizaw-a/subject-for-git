package jp.co.ginga.web.domain.service.stub;

import java.util.List;

import jp.co.ginga.infra.repository.facilityType.FacilityTypeEntity;
import jp.co.ginga.infra.repository.facilityType.FacilityTypeRepository;

public class StubFacilityTypeRepository implements FacilityTypeRepository {
	@Override
	public List<FacilityTypeEntity> findAll() {
		return null;
	}

	@Override
	public List<FacilityTypeEntity> findByFgDelete(int fgDelete) {
		return null;
	}

	@Override
	public FacilityTypeEntity findOneById(int facilityTypeId) {
		return null;
	}

	@Override
	public List<FacilityTypeEntity> findByTypeName(String name) {
		return null;
	}

	@Override
	public int insert(FacilityTypeEntity facilityTypeEntity) {
		return 0;
	}

	@Override
	public int update(FacilityTypeEntity facilityTypeEntity) {
		return 0;
	}

	@Override
	public int delete(int facilityTypeId) {
		return 0;
	}

}
