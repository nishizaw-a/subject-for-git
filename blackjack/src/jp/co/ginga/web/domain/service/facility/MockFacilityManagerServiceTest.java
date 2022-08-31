package jp.co.ginga.web.domain.service.facility;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.ginga.infra.repository.facility.FacilityEntity;
import jp.co.ginga.infra.repository.facility.FacilityRepository;
import jp.co.ginga.infra.repository.facilityType.FacilityTypeEntity;
import jp.co.ginga.infra.repository.facilityType.FacilityTypeRepository;
import jp.co.ginga.infra.repository.user.UserEntity;
import jp.co.ginga.web.domain.service.util.constant.ServiesConst;
import jp.co.ginga.web.domain.service.util.dto.facility.FacilityDto;
import jp.co.ginga.web.domain.service.util.dto.facilityType.FacilityTypeDto;
import jp.co.ginga.web.domain.service.util.dto.user.UserDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MockFacilityManagerServiceTest {

	/**
	 * テストデータ
	 */
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

	private UserEntity userEntity;
	private UserDto dto;

	private int f_facilityId = 1;
	private String f_name = "テスト施設";
	private int f_capacity = 123;
	private FacilityTypeEntity f_facilityTypeEntity;
	private Timestamp f_insertDate = null;
	private UserEntity f_insertUserEntity;
	private Timestamp f_updateDate = null;
	private UserEntity f_updateUserEntity;
	private String optimisticRockValue;
	private int ft_facilityTypeId = 1;
	private String ft_name = "type_Test";
	private Date ft_insertDate = null;
	private UserEntity ft_insertUserEntity = userEntity;
	private Date ft_updateDate = null;
	private UserEntity ft_updateUserEntity = userEntity;
	private Date ft_deleteDate = null;
	private UserEntity ft_deleteUserEntity = userEntity;
	private boolean ft_fgDelete = false;

	private String upOptimisticRockValue = null;

	/**
	 * テスト用Entity
	 */
	private FacilityEntity facilityEntity;
	private FacilityEntity nullFacilityEntity;
	private FacilityTypeEntity facilityTypeEntity;
	private List<FacilityEntity> facilityEntityList;
	private List<FacilityTypeEntity> facilityTypeEntityList;

	private List<FacilityEntity> nullFacilityEntityList;
	private List<FacilityTypeEntity> nullFacilityTypeEntityList;

	/**
	 * テスト用Dto
	 */
	private FacilityDto fDto;
	private List<FacilityDto> facilityDtoList;
	private FacilityTypeDto ftDto;
	private List<FacilityTypeDto> typeDtoList;

	/**
	 * テスト用ManagerDto
	 */
	private FacilityManagerDto fmDto;
	//update用

	//update(optimisticRockValue)エラー用
	private FacilityManagerDto optFmDto;

	@Mock
	private FacilityRepository repoFacility;
	@Mock
	private FacilityTypeRepository repoType;

	/**
	 * テスト実施クラス
	 */
	@InjectMocks
	@Autowired
	FacilityManagerService service = new FacilityManagerServiceImpl();

	/**
	 * 宣言したインスタンスを初期化してMock化
	 */
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * テストデータのセットアップ
	 */
	@BeforeEach
	public void createData() {
		this.userEntity = new UserEntity(u_userId, u_userName, u_password, u_passUpdateDate, u_gender,
				u_birthday, u_contact, u_mailAddress, u_loginMissTimes, u_unlock, u_enabled, u_userDueDate,
				u_insertDate, u_updateDate);
		this.f_insertUserEntity = userEntity;
		this.f_updateUserEntity = userEntity;
		this.facilityTypeEntity = new FacilityTypeEntity(ft_facilityTypeId, ft_name, ft_insertDate,
				ft_insertUserEntity, ft_updateDate, ft_updateUserEntity, ft_deleteDate, ft_deleteUserEntity,
				ft_fgDelete);
		this.f_facilityTypeEntity = facilityTypeEntity;
		this.facilityEntity = new FacilityEntity(f_facilityId, f_name, f_capacity,
				f_facilityTypeEntity, f_insertDate, f_insertUserEntity, f_updateDate, f_updateUserEntity);
		this.nullFacilityEntity = new FacilityEntity();

		this.facilityEntityList = new ArrayList<FacilityEntity>();
		this.facilityEntityList.add(facilityEntity);

		this.facilityTypeEntityList = new ArrayList<FacilityTypeEntity>();
		this.facilityTypeEntityList.add(facilityTypeEntity);

		this.nullFacilityEntityList = new ArrayList<FacilityEntity>();
		this.nullFacilityTypeEntityList = new ArrayList<FacilityTypeEntity>();

		this.dto = new UserDto();
		this.dto.setUserId(u_userId);
		this.dto.setUserName(u_userName);
		this.dto.setPassword(u_password);
		this.dto.setPassUpdateDate(u_passUpdateDate);
		this.dto.setGender(u_gender);
		this.dto.setBirthday(u_birthday);
		this.dto.setContact(u_contact);
		this.dto.setMailAddress(u_mailAddress);
		this.dto.setLoginMissTimes(u_loginMissTimes);
		this.dto.setUnlock(u_unlock);
		this.dto.setEnabled(u_enabled);
		this.dto.setUserDueDate(u_userDueDate);
		this.dto.setInsertDate(u_insertDate);

		this.ftDto = new FacilityTypeDto();
		this.ftDto.setFacilityTypeId(ft_facilityTypeId);
		this.ftDto.setName(ft_name);
		this.ftDto.setInsertDate(ft_insertDate);
		this.ftDto.setInsertUserDto(dto);
		this.fDto = new FacilityDto();
		this.fDto.setFacilityId(f_facilityId);
		this.fDto.setName(f_name);
		this.fDto.setCapacity(f_capacity);
		this.fDto.setFacilityTypeDto(ftDto);
		this.fDto.setUserDto(dto);

		this.facilityDtoList = new ArrayList<FacilityDto>();
		this.facilityDtoList.add(fDto);

		this.typeDtoList = new ArrayList<FacilityTypeDto>();
		this.typeDtoList.add(ftDto);

		this.optimisticRockValue = f_facilityId + "," + f_name + "," + f_capacity + "," + f_facilityTypeEntity
				+ "," +
				f_insertDate + "," + f_insertUserEntity + "," + f_updateDate + "," + f_updateUserEntity;

		this.fmDto = new FacilityManagerDto();
		this.fmDto.setOptimisticRockValue(optimisticRockValue);
		this.fmDto.setFacilityDto(fDto);
		this.fmDto.setFacilityDtoList(facilityDtoList);
		this.fmDto.setFacilityTypeDto(ftDto);
		this.fmDto.setFacilityTypeDtoList(typeDtoList);

		this.optFmDto = new FacilityManagerDto();
		this.optFmDto.setFacilityDto(fDto);
		this.optFmDto.setFacilityDtoList(facilityDtoList);
		this.optFmDto.setFacilityTypeDto(ftDto);
		this.optFmDto.setFacilityTypeDtoList(typeDtoList);
		this.optFmDto.setOptimisticRockValue(upOptimisticRockValue);

	}

	/**
	 * getFacilityList 正常系001
	 */
	@Test
	public void testGetFacilityList_normal_001() {
		System.out.println("testGetFacilityList_normal_001");

		when(this.repoFacility.findAll()).thenReturn(this.facilityEntityList);

		FacilityManagerDto result = service.getFacilityList();

		assertEquals(f_facilityId, result.getFacilityDtoList().get(0).getFacilityId());
		assertEquals(f_name, result.getFacilityDtoList().get(0).getName());
		assertEquals(f_capacity, result.getFacilityDtoList().get(0).getCapacity());
		assertEquals(ft_facilityTypeId, result.getFacilityDtoList().get(0).getFacilityTypeDto().getFacilityTypeId());
		assertEquals(ft_name, result.getFacilityDtoList().get(0).getFacilityTypeDto().getName());
		assertEquals(ft_insertDate, result.getFacilityDtoList().get(0).getFacilityTypeDto().getInsertDate());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).findAll();

	}

	/**
	 * getFacilityList 正常系002
	 */
	@Test
	public void testGetFacilityList_normal_002() {
		System.out.println("testGetFacilityList_normal_002");

		when(this.repoFacility.findAll()).thenReturn(this.nullFacilityEntityList);

		FacilityManagerDto result = service.getFacilityList();

		assertEquals(0, result.getFacilityDtoList().size());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).findAll();

	}

	/**
	 *  getFacilityDetailByFacilityId 正常系001
	 */
	@Test
	public void testGetFacilityDetailByFacilityId_normal_001() {
		System.out.println("testGetFacilityDetailByFacilityId_normal_001");

		when(this.repoFacility.findByFacilityId(f_facilityId)).thenReturn(this.facilityEntity);
		when(this.repoType.findAll()).thenReturn(this.facilityTypeEntityList);

		FacilityManagerDto result = service.getFacilityDetailByFacilityId(f_facilityId);

		assertEquals(f_facilityId, result.getFacilityDto().getFacilityId());
		assertEquals(f_name, result.getFacilityDto().getName());
		assertEquals(f_capacity, result.getFacilityDto().getCapacity());
		assertEquals(ft_facilityTypeId, result.getFacilityDto().getFacilityTypeDto().getFacilityTypeId());
		assertEquals(ft_name, result.getFacilityDto().getFacilityTypeDto().getName());
		assertEquals(ft_insertDate, result.getFacilityDto().getFacilityTypeDto().getInsertDate());

		assertEquals(ft_facilityTypeId, result.getFacilityTypeDtoList().get(0).getFacilityTypeId());
		assertEquals(ft_name, result.getFacilityTypeDtoList().get(0).getName());
		assertEquals(ft_insertDate, result.getFacilityTypeDtoList().get(0).getInsertDate());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).findByFacilityId(f_facilityId);
		verify(this.repoType, times(1)).findAll();
	}

	/**
	 *  getFacilityDetailByFacilityId 正常系002
	 */
	@Test
	public void testGetFacilityDetailByFacilityId_normal_002() {
		System.out.println("testGetFacilityDetailByFacilityId_normal_002");

		when(this.repoFacility.findByFacilityId(f_facilityId)).thenReturn(this.nullFacilityEntity);
		when(this.repoType.findAll()).thenReturn(this.nullFacilityTypeEntityList);

		FacilityManagerDto result = service.getFacilityDetailByFacilityId(f_facilityId);

		assertEquals(0, result.getFacilityTypeDtoList().size());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).findByFacilityId(f_facilityId);
		verify(this.repoType, times(1)).findAll();

	}

	/**
	 * getFacilityByName 正常系001
	 */
	@Test
	public void testGetFacilityByName_normal_001() {
		System.out.println("testgetFacilityByName_normal_001");

		when(this.repoFacility.findByName(f_name)).thenReturn(this.facilityEntityList);

		FacilityManagerDto result = service.getFacilityByName(f_name);

		assertEquals(1, result.getFacilityDtoList().size());
		assertEquals(f_facilityId, result.getFacilityDtoList().get(0).getFacilityId());
		assertEquals(f_name, result.getFacilityDtoList().get(0).getName());
		assertEquals(f_capacity, result.getFacilityDtoList().get(0).getCapacity());
		assertEquals(ft_facilityTypeId, result.getFacilityDtoList().get(0).getFacilityTypeDto().getFacilityTypeId());
		assertEquals(ft_name, result.getFacilityDtoList().get(0).getFacilityTypeDto().getName());
		assertEquals(ft_insertDate, result.getFacilityDtoList().get(0).getFacilityTypeDto().getInsertDate());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).findByName(f_name);

	}

	/**
	 *  getFacilityByName 正常系002
	 */

	@Test
	public void testGetFacilityByName_normal_002() {
		System.out.println("testgetFacilityByName_normal_002");

		when(this.repoFacility.findByName(f_name)).thenReturn(this.nullFacilityEntityList);

		FacilityManagerDto result = service.getFacilityByName(f_name);

		assertEquals(ServiesConst.NO_DATA, result.getResult());
		assertEquals(0, result.getFacilityDtoList().size());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).findByName(f_name);

	}

	/**
	 *  getFacilityByName 異常系003
	 */
	@Test
	public void testGetFacilityByName_normal_003() {
		System.out.println("testgetFacilityByName_normal_003");

		when(this.repoFacility.findByName(f_name)).thenReturn(null);

		FacilityManagerDto result = service.getFacilityByName(f_name);

		assertEquals(ServiesConst.ERROR, result.getResult());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).findByName(f_name);

	}

	/**
	 * add 正常系001
	 */
	@Test
	public void testAdd_normal_001() {
		System.out.println("testAdd_normal_001");

		when(this.repoFacility.insert(this.facilityEntity)).thenReturn(1);

		FacilityManagerDto result = service.add(this.fmDto);

		assertEquals(ServiesConst.THERE_IS_DATA, result.getResult());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).insert(this.facilityEntity);

	}

	/**
	 *  add 正常系002
	 */
	@Test
	public void testAdd_normal_002() {
		System.out.println("testAdd_normal_002");

		when(this.repoFacility.insert(this.facilityEntity)).thenReturn(0);

		FacilityManagerDto result = service.add(fmDto);

		assertEquals(ServiesConst.NO_DATA, result.getResult());

		// モック呼び出し回数
		verify(this.repoFacility, times(1)).insert(this.facilityEntity);

	}

	/**
	 * update 正常系001
	 */
	@Test
	public void testUpdate_normal_001() {
		System.out.println("testUpdate_normal_001");

		when(this.repoFacility.findByFacilityId(this.fmDto.getFacilityDto().getFacilityId()))
				.thenReturn(this.facilityEntity);
		when(this.repoFacility.update(any())).thenReturn(1);

		FacilityManagerDto result = service.update(fmDto);

		assertEquals(ServiesConst.OK, result.getResult());

		verify(this.repoFacility, times(1)).findByFacilityId(this.fmDto.getFacilityDto().getFacilityId());
		verify(this.repoFacility, times(1)).update(any());

	}

	/**
	 *  update 正常系002 データあり楽観的ロック
	 */

	@Test
	public void testUpdate_normal_002() {
		System.out.println("testUpdate_normal_002");

		when(this.repoFacility.findByFacilityId(this.fmDto.getFacilityDto().getFacilityId()))
				.thenReturn(this.facilityEntity);

		FacilityManagerDto result = service.update(optFmDto);

		assertEquals(ServiesConst.OPTIMISTIC_ROCK_ERROR, result.getResult());

		verify(this.repoFacility, times(1)).findByFacilityId(this.fmDto.getFacilityDto().getFacilityId());

	}

	/**
	 * update 異常系001 データなし
	 */

	@Test
	public void testUpdate_abnormal_001() {
		System.out.println("testUpdate_abnormal_001");

		when(this.repoFacility.findByFacilityId(this.fmDto.getFacilityDto().getFacilityId()))
				.thenReturn(this.facilityEntity);
		when(this.repoFacility.update(any())).thenReturn(0);

		FacilityManagerDto result = service.update(fmDto);

		assertEquals(ServiesConst.ERROR, result.getResult());

		verify(this.repoFacility, times(1)).findByFacilityId(this.fmDto.getFacilityDto().getFacilityId());

	}

	/**
	 * delete 正常系001
	 */
	@Test
	public void testDelete_normal_001() {
		System.out.println("testDelete_normal_001");

		when(this.repoFacility.delete(this.fmDto.getFacilityDto().getFacilityId())).thenReturn(1);

		boolean check = service.delete(fmDto);

		assertTrue(check);

		verify(this.repoFacility, times(1)).delete(this.fmDto.getFacilityDto().getFacilityId());

	}

	/**
	 * delete 正常系001
	 */
	@Test
	public void testDelete_abnormal_001() {
		System.out.println("testDelete_abnormal_001");

		when(this.repoFacility.delete(this.fmDto.getFacilityDto().getFacilityId())).thenReturn(0);

		boolean check = service.delete(fmDto);

		assertFalse(check);

		verify(this.repoFacility, times(1)).delete(this.fmDto.getFacilityDto().getFacilityId());

	}
}
