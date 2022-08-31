package jp.co.ginga.web.domain.service.facility;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.ginga.infra.repository.facility.FacilityEntity;
import jp.co.ginga.infra.repository.facility.FacilityRepository;
import jp.co.ginga.infra.repository.facilityType.FacilityTypeEntity;
import jp.co.ginga.infra.repository.facilityType.FacilityTypeRepository;
import jp.co.ginga.infra.repository.user.UserEntity;
import jp.co.ginga.web.domain.service.stub.StubFacilityRepository;
import jp.co.ginga.web.domain.service.stub.StubFacilityTypeRepository;
import jp.co.ginga.web.domain.service.util.constant.ServiesConst;
import jp.co.ginga.web.domain.service.util.dto.facility.FacilityDto;
import jp.co.ginga.web.domain.service.util.dto.facilityType.FacilityTypeDto;
import jp.co.ginga.web.domain.service.util.dto.user.UserDto;
import jp.co.ginga.web.domain.service.util.dto.userrole.UserRoleDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StubFacilityManagerServiceTest {

	@Autowired
	FacilityManagerService service;

	private String u_userId = "test";
	private String u_userName = "テストユーザー";
	private String u_password = "pass1";
	private Timestamp u_passUpdateDate = null;
	private String u_gender = "男";
	private String u_birthday = "2021-04-01";
	private String u_contact = "090-1234-5678";
	private String u_mailAddress = "test@xxx.co.jp";
	private int u_loginMissTimes = 0;
	private boolean u_unlock = true;
	private boolean u_enabled = true;
	private Timestamp u_userDueDate = null;
	private Timestamp u_insertDate = null;
	private Timestamp u_updateDate = null;
	private String r_roleId = "tests";
	private String r_roleName = "ROLE_Test";
	private String ur_userId = "test";
	private String ur_roleId = "tests";
	private String ur_roleId1 = "tests2";

	private UserEntity userEntity = new UserEntity(u_userId, u_userName, u_password, u_passUpdateDate, u_gender,
			u_birthday, u_contact, u_mailAddress, u_loginMissTimes, u_unlock, u_enabled, u_userDueDate,
			u_insertDate, u_updateDate);

	//テストデータ
	private int f_facilityId = 1;
	private String f_name = "テスト施設";
	private int f_capacity = 123;

	private Timestamp f_insertDate = null;
	private UserEntity f_insertUserEntity = null;
	private Timestamp f_updateDate = null;
	private UserEntity f_updateUserEntity = null;

	private int ft_facilityTypeId = 1;
	private String ft_name = "type_Test";
	private Date ft_insertDate = null;
	private UserEntity ft_insertUserEntity = userEntity;
	private Date ft_updateDate = null;
	private UserEntity ft_updateUserEntity = userEntity;
	private Date ft_deleteDate = null;
	private UserEntity ft_deleteUserEntity = userEntity;
	private boolean ft_fgDelete = false;

	private FacilityTypeEntity facilityTypeEntity = new FacilityTypeEntity(ft_facilityTypeId, ft_name, ft_insertDate,
			ft_insertUserEntity, ft_updateDate, ft_updateUserEntity, ft_deleteDate, ft_deleteUserEntity, ft_fgDelete);
	private FacilityTypeEntity f_facilityTypeEntity = facilityTypeEntity;

	/**
	 * getFacilityList(); 正常系001 リストサイズ1
	 */
	@Test
	public void testGetFacilityList_normal_001() {
		System.out.println("testGetFacilityList_normal_001");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public List<FacilityEntity> findAll() {
				List<FacilityEntity> facilityEntitiyList = new ArrayList<FacilityEntity>();

				FacilityEntity facilityEntity = new FacilityEntity(f_facilityId, f_name, f_capacity,
						f_facilityTypeEntity, f_insertDate, f_insertUserEntity, f_updateDate, f_updateUserEntity);
				facilityEntitiyList.add(facilityEntity);

				return facilityEntitiyList;
			}
		};

		service.setFacilityRepository(facilityRepository);

		//テスト実施メソッド
		FacilityManagerDto result = service.getFacilityList();

		//テスト検証メソッド
		assertEquals(1, result.getFacilityDtoList().size());
		assertEquals(f_facilityId, result.getFacilityDtoList().get(0).getFacilityId());
		assertEquals(f_name, result.getFacilityDtoList().get(0).getName());
		assertEquals(f_capacity, result.getFacilityDtoList().get(0).getCapacity());
		assertEquals(ft_facilityTypeId, result.getFacilityDtoList().get(0).getFacilityTypeDto().getFacilityTypeId());
		assertEquals(ft_name, result.getFacilityDtoList().get(0).getFacilityTypeDto().getName());
		assertEquals(ft_insertDate, result.getFacilityDtoList().get(0).getFacilityTypeDto().getInsertDate());

	}

	/**
	 * getFacilityList(); 正常系002 データなし
	 */

	@Test
	public void testGetFacilityList_normal_002() {
		System.out.println("testGetFacilityList_normal_002");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public List<FacilityEntity> findAll() {
				List<FacilityEntity> facilityEntitiyList = new ArrayList<FacilityEntity>();

				return facilityEntitiyList;
			}
		};

		service.setFacilityRepository(facilityRepository);

		// テスト実施メソッド
		FacilityManagerDto result = service.getFacilityList();

		assertEquals(0, result.getFacilityDtoList().size());

	}

	/**
	 *  getFacilityDetailByFacilityId(int facilityId); 正常系001 データあり
	 */

	@Test
	public void testGetFacilityDetailByFacilityId_normal_001() {
		System.out.println("testGetFacilityDetailByFacilityId_normal_001");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public FacilityEntity findByFacilityId(int facilityId) {

				FacilityEntity facilityEntity = new FacilityEntity(f_facilityId, f_name, f_capacity,
						f_facilityTypeEntity, f_insertDate, f_insertUserEntity, f_updateDate, f_updateUserEntity);

				return facilityEntity;
			}
		};

		FacilityTypeRepository facilityTypeRepository = new StubFacilityTypeRepository() {

			@Override
			public List<FacilityTypeEntity> findAll() {
				List<FacilityTypeEntity> facilityTypeEntitiyList = new ArrayList<FacilityTypeEntity>();
				FacilityTypeEntity TypeEntity = facilityTypeEntity;
				facilityTypeEntitiyList.add(TypeEntity);
				return facilityTypeEntitiyList;
			}
		};

		service.setFacilityRepository(facilityRepository);
		service.setFacilityTypeRepository(facilityTypeRepository);

		FacilityManagerDto result = service.getFacilityDetailByFacilityId(f_facilityId);

		assertEquals(f_facilityId, result.getFacilityDto().getFacilityId());
		assertEquals(f_name, result.getFacilityDto().getName());
		assertEquals(f_capacity, result.getFacilityDto().getCapacity());
		assertEquals(ft_facilityTypeId, result.getFacilityDto().getFacilityTypeDto().getFacilityTypeId());
		assertEquals(ft_name, result.getFacilityDto().getFacilityTypeDto().getName());
		assertEquals(ft_insertDate, result.getFacilityDto().getFacilityTypeDto().getInsertDate());

		assertEquals(1, result.getFacilityTypeDtoList().size());
		assertEquals(ft_facilityTypeId, result.getFacilityTypeDtoList().get(0).getFacilityTypeId());
		assertEquals(ft_name, result.getFacilityTypeDtoList().get(0).getName());
		assertEquals(ft_insertDate, result.getFacilityTypeDtoList().get(0).getInsertDate());

	}

	/**
	 *  getFacilityDetailByFacilityId(int facilityId); 正常系002 データなし
	 */

	@Test
	public void testGetFacilityDetailByFacilityId_normal_002() {
		System.out.println("testGetFacilityDetailByFacilityId_normal_002");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public FacilityEntity findByFacilityId(int facilityId) {

				FacilityEntity facilityEntity = new FacilityEntity();

				return facilityEntity;
			}
		};

		FacilityTypeRepository facilityTypeRepository = new StubFacilityTypeRepository() {

			@Override
			public List<FacilityTypeEntity> findAll() {
				List<FacilityTypeEntity> facilityTypeEntitiyList = new ArrayList<FacilityTypeEntity>();

				return facilityTypeEntitiyList;
			}
		};

		service.setFacilityRepository(facilityRepository);
		service.setFacilityTypeRepository(facilityTypeRepository);

		FacilityManagerDto result = service.getFacilityDetailByFacilityId(f_facilityId);

		assertEquals(0, result.getFacilityDto().getFacilityId());
		assertEquals(0, result.getFacilityTypeDtoList().size());

	}

	/*
	 * getFacilityByName(String name); 正常系001 データあり
	 */

	@Test
	public void testGetFacilityByName_normal_001() {
		System.out.println("testgetFacilityByName_normal_001");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public List<FacilityEntity> findByName(String name) {
				List<FacilityEntity> facilityEntitiyList = new ArrayList<FacilityEntity>();
				FacilityEntity facilityEntity = new FacilityEntity(f_facilityId, f_name, f_capacity,
						f_facilityTypeEntity, f_insertDate, f_insertUserEntity, f_updateDate, f_updateUserEntity);
				facilityEntitiyList.add(facilityEntity);

				return facilityEntitiyList;
			}
		};

		service.setFacilityRepository(facilityRepository);

		FacilityManagerDto result = service.getFacilityByName(f_name);

		assertEquals(ServiesConst.THERE_IS_DATA, result.getResult());

		assertEquals(1, result.getFacilityDtoList().size());
		assertEquals(f_facilityId, result.getFacilityDtoList().get(0).getFacilityId());
		assertEquals(f_name, result.getFacilityDtoList().get(0).getName());
		assertEquals(f_capacity, result.getFacilityDtoList().get(0).getCapacity());
		assertEquals(ft_facilityTypeId, result.getFacilityDtoList().get(0).getFacilityTypeDto().getFacilityTypeId());
		assertEquals(ft_name, result.getFacilityDtoList().get(0).getFacilityTypeDto().getName());
		assertEquals(ft_insertDate, result.getFacilityDtoList().get(0).getFacilityTypeDto().getInsertDate());

	}

	/*
	 *  getFacilityByName(String name); 正常系002 データなし
	 */

	@Test
	public void testGetFacilityByName_normal_002() {
		System.out.println("testgetFacilityByName_normal_002");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public List<FacilityEntity> findByName(String name) {
				List<FacilityEntity> facilityEntitiyList = new ArrayList<FacilityEntity>();

				return facilityEntitiyList;
			}
		};

		service.setFacilityRepository(facilityRepository);

		FacilityManagerDto result = service.getFacilityByName(f_name);

		assertEquals(ServiesConst.NO_DATA, result.getResult());
		assertEquals(0, result.getFacilityDtoList().size());

	}

	/*
	 *  getFacilityByName(String name); 異常系003 null
	 */

	@Test
	public void testGetFacilityByName_normal_003() {
		System.out.println("testgetFacilityByName_normal_003");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public List<FacilityEntity> findByName(String name) {
				List<FacilityEntity> facilityEntitiyList = new ArrayList<FacilityEntity>();
				FacilityEntity facilityEntity = new FacilityEntity();
				facilityEntitiyList.add(facilityEntity);

				return null;
			}
		};

		service.setFacilityRepository(facilityRepository);

		FacilityManagerDto result = service.getFacilityByName(f_name);

		assertEquals(ServiesConst.ERROR, result.getResult());
	}

	/*
	 * add(FacilityManagerDto facilityManagerDto); 正常系001 データあり
	 */

	@Test
	public void testAdd_normal_001() {
		System.out.println("testAdd_normal_001");

		FacilityManagerDto fmDto = new FacilityManagerDto();
		List<UserRoleDto> urDtoList = new ArrayList<UserRoleDto>();
		UserRoleDto urDto = new UserRoleDto(u_userId, ur_roleId);
		urDtoList.add(urDto);
		UserDto userDto = new UserDto(u_userId, u_userName, u_password, u_passUpdateDate, u_gender, u_birthday,
				u_contact, u_mailAddress, u_loginMissTimes, u_unlock, u_enabled, u_userDueDate, u_insertDate,
				u_updateDate, urDtoList);
		FacilityTypeDto facilityTypeDto = new FacilityTypeDto(ft_facilityTypeId, ft_name, ft_insertDate, userDto);

		fmDto.setFacilityDto(new FacilityDto(f_facilityId, f_name, f_capacity, facilityTypeDto, userDto));

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public int insert(FacilityEntity entity) {
				return 1;
			}
		};

		service.setFacilityRepository(facilityRepository);

		FacilityManagerDto result = service.add(fmDto);

		assertEquals(ServiesConst.THERE_IS_DATA, result.getResult());
	}

	/*
	 *  add(FacilityManagerDto facilityManagerDto); 正常系002 データなし
	 */

	@Test
	public void testAdd_normal_002() {
		System.out.println("testAdd_normal_002");

		FacilityManagerDto fmDto = new FacilityManagerDto();
		List<UserRoleDto> urDtoList = new ArrayList<UserRoleDto>();
		UserRoleDto urDto = new UserRoleDto();
		urDtoList.add(urDto);
		UserDto userDto = new UserDto();
		FacilityTypeDto facilityTypeDto = new FacilityTypeDto();

		fmDto.setFacilityDto(new FacilityDto());
		fmDto.getFacilityDto().setUserDto(userDto);
		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public int insert(FacilityEntity entity) {
				return 0;
			}
		};

		service.setFacilityRepository(facilityRepository);

		FacilityManagerDto result = service.add(fmDto);

		assertEquals(ServiesConst.NO_DATA, result.getResult());
	}

	/*
	 * update(FacilityManagerDto facilityManagerDto); 正常系001 データあり
	 */

	@Test
	public void testUpdate_normal_001() {
		System.out.println("testUpdate_normal_001");

		FacilityManagerDto fmDto = new FacilityManagerDto();
		List<UserRoleDto> urDtoList = new ArrayList<UserRoleDto>();
		UserRoleDto urDto = new UserRoleDto(u_userId, ur_roleId);
		urDtoList.add(urDto);
		UserDto userDto = new UserDto(u_userId, u_userName, u_password, u_passUpdateDate, u_gender, u_birthday,
				u_contact, u_mailAddress, u_loginMissTimes, u_unlock, u_enabled, u_userDueDate, u_insertDate,
				u_updateDate, urDtoList);
		FacilityTypeDto facilityTypeDto = new FacilityTypeDto(ft_facilityTypeId, ft_name, ft_insertDate, userDto);

		fmDto.setFacilityDto(new FacilityDto(f_facilityId, f_name, f_capacity, facilityTypeDto, userDto));
		fmDto.setOptimisticRockValue("1,テスト施設,123,null,null,null,null,null");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public int update(FacilityEntity facilityEntity) {
				return 1;

			}

			@Override
			public FacilityEntity findByFacilityId(int facilityId) {
				FacilityEntity facilityEntity = new FacilityEntity(f_facilityId, f_name, f_capacity,
						null, f_insertDate, null, f_updateDate, null);
				return facilityEntity;
			}

		};

		service.setFacilityRepository(facilityRepository);

		FacilityManagerDto result = service.update(fmDto);

		assertEquals(ServiesConst.OK, result.getResult());
	}

	/*
	 *  update(FacilityManagerDto facilityManagerDto); 正常系002 データあり楽観的ロック
	 */

	@Test
	public void testUpdate_normal_002() {
		System.out.println("testUpdate_normal_002");

		FacilityManagerDto fmDto = new FacilityManagerDto();
		List<UserRoleDto> urDtoList = new ArrayList<UserRoleDto>();
		UserRoleDto urDto = new UserRoleDto(u_userId, ur_roleId);
		urDtoList.add(urDto);
		UserDto userDto = new UserDto(u_userId, u_userName, u_password, u_passUpdateDate, u_gender, u_birthday,
				u_contact, u_mailAddress, u_loginMissTimes, u_unlock, u_enabled, u_userDueDate, u_insertDate,
				u_updateDate, urDtoList);
		FacilityTypeDto facilityTypeDto = new FacilityTypeDto(ft_facilityTypeId, ft_name, ft_insertDate, userDto);

		fmDto.setFacilityDto(new FacilityDto(f_facilityId, f_name, f_capacity, facilityTypeDto, userDto));

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public FacilityEntity findByFacilityId(int facilityId) {
				FacilityEntity facilityEntity = new FacilityEntity(f_facilityId, f_name, f_capacity,
						null, f_insertDate, null, f_updateDate, null);
				return facilityEntity;
			}

		};

		service.setFacilityRepository(facilityRepository);

		FacilityManagerDto result = service.update(fmDto);

		assertEquals(ServiesConst.OPTIMISTIC_ROCK_ERROR, result.getResult());
	}

	/*
	 * update(FacilityManagerDto facilityManagerDto); 異常系001 データなし
	 */

	@Test
	public void testUpdate_abnormal_001() {
		System.out.println("testUpdate_abnormal_001");

		FacilityManagerDto fmDto = new FacilityManagerDto();
		List<UserRoleDto> urDtoList = new ArrayList<UserRoleDto>();
		UserRoleDto urDto = new UserRoleDto();
		urDtoList.add(urDto);

		UserDto userDto = new UserDto();

		fmDto.setFacilityDto(new FacilityDto());
		fmDto.getFacilityDto().setUserDto(userDto);
		fmDto.setOptimisticRockValue("1,テスト施設,123,null,null,null,null,null");

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public FacilityEntity findByFacilityId(int facilityId) {
				FacilityEntity facilityEntity = new FacilityEntity(f_facilityId, f_name, f_capacity,
						null, f_insertDate, null, f_updateDate, null);
				return facilityEntity;
			}

		};

		service.setFacilityRepository(facilityRepository);

		FacilityManagerDto result = service.update(fmDto);

		assertEquals(ServiesConst.ERROR, result.getResult());
	}

	/*
	 * delete(FacilityManagerDto facilityManagerDto); 正常系001 データあり
	 */
	@Test
	public void testDelete_normal_001() {
		System.out.println("testDelete_normal_001");

		FacilityManagerDto fmDto = new FacilityManagerDto();
		List<UserRoleDto> urDtoList = new ArrayList<UserRoleDto>();
		UserRoleDto urDto = new UserRoleDto(u_userId, ur_roleId);
		urDtoList.add(urDto);
		UserDto userDto = new UserDto(u_userId, u_userName, u_password, u_passUpdateDate, u_gender, u_birthday,
				u_contact, u_mailAddress, u_loginMissTimes, u_unlock, u_enabled, u_userDueDate, u_insertDate,
				u_updateDate, urDtoList);
		FacilityTypeDto facilityTypeDto = new FacilityTypeDto(ft_facilityTypeId, ft_name, ft_insertDate, userDto);

		fmDto.setFacilityDto(new FacilityDto(f_facilityId, f_name, f_capacity, facilityTypeDto, userDto));

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public int delete(int facilityId) {
				return 1;
			}

		};

		service.setFacilityRepository(facilityRepository);

		boolean check = service.delete(fmDto);

		assertTrue(check);
	}

	/*
	 * delete(FacilityManagerDto facilityManagerDto); 異常系001 データなし
	 */

	@Test
	public void testDelete_abnormal_001() {
		System.out.println("testDelete_abnormal_001");

		FacilityManagerDto fmDto = new FacilityManagerDto();
		List<UserRoleDto> urDtoList = new ArrayList<UserRoleDto>();
		UserRoleDto urDto = new UserRoleDto();
		urDtoList.add(urDto);

		UserDto userDto = new UserDto();

		fmDto.setFacilityDto(new FacilityDto());
		fmDto.getFacilityDto().setUserDto(userDto);

		FacilityRepository facilityRepository = new StubFacilityRepository() {

			@Override
			public int delete(int facilityId) {
				return 0;
			}

		};

		service.setFacilityRepository(facilityRepository);

		boolean check = service.delete(fmDto);

		assertFalse(check);
	}

}
