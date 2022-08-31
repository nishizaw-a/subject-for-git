package souko;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.ginga.infra.repository.facility.FacilityEntity;
import jp.co.ginga.infra.repository.facility.FacilityRepository;
import jp.co.ginga.infra.repository.facilityType.FacilityTypeEntity;
import jp.co.ginga.infra.repository.user.UserEntity;
import jp.co.ginga.infra.util.PostgreDataManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class FacilityRepositoryTest {

	@Autowired
	FacilityRepository facilityRepository;

	/**
	 * 施設情報 リポジトリ 全件検索処理 データ全件の場合
	 */

	@Test
	public void findAllMultipleData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);
			// CLEAN_INSERTでpgadminに登録しているデータを削除して再セットする

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		List<FacilityEntity> list = facilityRepository.findAll();

		assertEquals(list.size(), 3);

		for (int i = 0; i < list.size(); i++) {
			assertEquals(i, list.get(i).getFacilityId());
			assertEquals("施設" + (i + 1), list.get(i).getName());
			assertEquals(i + 1, list.get(i).getCapacity());

		}

	}

	/**
	 * 施設情報 リポジトリ 全件検索処理 データ1件の場合
	 */

	@Test
	public void findAllSingleData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_single_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_single_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		List<FacilityEntity> list = facilityRepository.findAll();

		assertEquals(list.size(), 1);
		assertEquals(0, list.get(0).getFacilityId());
		assertEquals("施設1", list.get(0).getName());
		assertEquals(1, list.get(0).getCapacity());
		assertEquals("会議室", list.get(0).getFacilityTypeEntity().getName());

	}

	/**
	 * 施設情報 リポジトリ 全件検索処理 データが0件の場合
	 */

	@Test
	public void findAllNoData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/util/setup_nodata.xml", DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
			// fail()：強制的に失敗になる
		}

		List<FacilityEntity> list = facilityRepository.findAll();

		assertEquals(list.size(), 0);
	}

	/**
	 * 施設情報 リポジトリ 施設IDによる検索処理 データがある場合
	 */
	@Test
	public void findByFacilityIdData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		FacilityEntity result = facilityRepository.findByFacilityId(0);

		assertEquals(0, result.getFacilityId());
		assertEquals("施設1", result.getName());
		assertEquals(1, result.getCapacity());

	}

	/**
	 * 施設情報 リポジトリ 施設IDによる検索処理 データが0件の場合
	 */
	@Test
	public void findByFacilityIdNoData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/util/setup_nodata.xml", DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		FacilityEntity result = facilityRepository.findByFacilityId(0);

		assertEquals(null, result);
	}

	/**
	 * 施設情報 リポジトリ 施設種別IDによる検索処理 データが複数ある場合
	 */
	@Test
	public void findByFacilityTypeIdMultipleData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		List<FacilityEntity> list = facilityRepository.findByFacilityTypeId(1);

		assertEquals(0, list.get(0).getFacilityId());
		assertEquals("施設1", list.get(0).getName());
		assertEquals(1, list.get(0).getCapacity());

		assertEquals(2, list.get(1).getFacilityId());
		assertEquals("施設3", list.get(1).getName());
		assertEquals(3, list.get(1).getCapacity());

	}

	/**
	 * 施設情報 リポジトリ 施設種別IDによる検索処理 データが1件ある場合
	 */
	@Test
	public void findByFacilityTypeIdSingleData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_single_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}
		List<FacilityEntity> list = facilityRepository.findByFacilityTypeId(1);

		assertEquals(list.size(), 1);
		assertEquals(0, list.get(0).getFacilityId());
		assertEquals("施設1", list.get(0).getName());
		assertEquals(1, list.get(0).getCapacity());
		assertEquals(1, list.get(0).getFacilityTypeEntity().getFacilityTypeId());
		assertEquals("会議室", list.get(0).getFacilityTypeEntity().getName());
	}

	/**
	 * 施設情報 リポジトリ 施設種別IDによる検索処理 データが0件の場合
	 */
	@Test
	public void findByFacilityTypeIdNoData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/util/setup_nodata.xml", DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		List<FacilityEntity> list = facilityRepository.findByFacilityTypeId(1);

		assertEquals(list.size(), 0);
	}

	/**
	 * 施設情報 リポジトリ 施設名前による検索処理 データがある場合
	 */
	@Test
	public void findByNameData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		List<FacilityEntity> list = facilityRepository.findByName("施設1");

		assertEquals(list.size(), 1);
		assertEquals(0, list.get(0).getFacilityId());
		assertEquals("施設1", list.get(0).getName());
		assertEquals(1, list.get(0).getCapacity());
		assertEquals(1, list.get(0).getFacilityTypeEntity().getFacilityTypeId());
		assertEquals("会議室", list.get(0).getFacilityTypeEntity().getName());
	}

	/**
	 * 施設情報 リポジトリ 施設名前による検索処理 データが0件の場合
	 */
	@Test
	public void findByNameNoData() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/util/setup_nodata.xml", DatabaseOperation.CLEAN_INSERT);

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		List<FacilityEntity> list = facilityRepository.findByName("施設1");

		assertEquals(list.size(), 0);
	}

	/**
	 * 施設情報 リポジトリ 登録処理 
	 */
	@Test
	public void insertOk() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);
			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		//テストデータ

		String datetime = "2022-04-01 00:00:00";

		String userId = "system3";
		String userName = "システム管理者3";
		String password = "pass3";
		Timestamp passUpdateDate = Timestamp.valueOf(datetime);
		String gender = "男";
		String birthday = "1月1日";
		String contact = "a";
		String mailAddress = "mail";
		int loginMissTimes = 1;
		boolean unlock = true;
		boolean enabled = true;
		Timestamp userDueDate = Timestamp.valueOf(datetime);
		Timestamp uInsertDate = Timestamp.valueOf(datetime);
		Timestamp uUpdateDate = Timestamp.valueOf(datetime);

		UserEntity usEntity = new UserEntity(userId, userName, password, passUpdateDate, gender, birthday, contact,
				mailAddress, loginMissTimes, unlock, enabled, userDueDate, uInsertDate, uUpdateDate);

		int facilityId = 3;
		String name = "施設4";
		int capacity = 4;

		int facilityTypeId = 2;
		String ftName = "応接室";
		Date ftInsertDate = null;
		UserEntity ftInsertUserEntity = usEntity;
		Date ftUpdateDate = null;
		UserEntity ftUpdateUserEntity = usEntity;
		Date deleteDate = null;
		UserEntity deleteUserEntity = usEntity;
		boolean fgDelete = false;

		FacilityTypeEntity facilityTypeEntity = new FacilityTypeEntity(facilityTypeId, ftName, ftInsertDate,
				ftInsertUserEntity,
				ftUpdateDate, ftUpdateUserEntity, deleteDate, deleteUserEntity, fgDelete);
		Timestamp insertDate = Timestamp.valueOf(datetime);
		UserEntity insertUserEntity = usEntity;
		Timestamp updateDate = Timestamp.valueOf(datetime);
		UserEntity updateUserEntity = usEntity;

		FacilityEntity facilityEntity = new FacilityEntity(facilityId, name, capacity, facilityTypeEntity,
				insertDate, insertUserEntity, updateDate, updateUserEntity);

		int result = facilityRepository.insert(facilityEntity);

		assertEquals(1, result);

		FacilityEntity result1 = facilityRepository.findByFacilityId(3);

	
		assertEquals(facilityEntity.getFacilityId(),result1.getFacilityId());
		assertEquals(true, facilityEntity.getName().equals(result1.getName()));
		assertEquals(facilityEntity.getCapacity(), result1.getCapacity());
		assertEquals(facilityEntity.getFacilityTypeEntity().getFacilityTypeId(),
				result1.getFacilityTypeEntity().getFacilityTypeId());
		assertEquals(facilityEntity.getFacilityTypeEntity().getName(), result1.getFacilityTypeEntity().getName());

		// Timestamp
		assertEquals(false, facilityEntity.getInsertDate().equals(result1.getInsertDate()));

	}

	/**
	 * 施設情報 リポジトリ 登録処理 施設名異常系　２１文字
	 */
	@Test
	public void insertNo() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);
			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		//テストデータ

		String f_datetime = "2022-04-01 00:00:00";

		int facilityId = 4;
		String name = "施設4444444444444444444";
		int capacity = 4;

		int facilityTypeId = 2;
		String ftName = "応接室";
		Date ftInsertDate = null;
		UserEntity ftInsertUserEntity = null;
		Date ftUpdateDate = null;
		UserEntity ftUpdateUserEntity = null;
		Date deleteDate = null;
		UserEntity deleteUserEntity = null;
		boolean fgDelete = false;

		FacilityTypeEntity facilityTypeEntity = new FacilityTypeEntity(facilityTypeId, ftName, ftInsertDate,
				ftInsertUserEntity,
				ftUpdateDate, ftUpdateUserEntity, deleteDate, deleteUserEntity, fgDelete);
		Timestamp insertDate = Timestamp.valueOf(f_datetime);
		UserEntity insertUserEntity = null;
		Timestamp updateDate = Timestamp.valueOf(f_datetime);
		UserEntity updateUserEntity = null;

		FacilityEntity facilityEntity = new FacilityEntity(facilityId, name, capacity, facilityTypeEntity,
				insertDate, insertUserEntity, updateDate, updateUserEntity);
		try {
			// テスト実施メソッド
			facilityRepository.insert(facilityEntity);
			fail("例外発生なし");
		} catch (Exception e) {
			// スキーマ違反例外を期待
			assertThat(e, is(instanceOf(DataIntegrityViolationException.class)));
		}
	}

	/**
	 * 施設情報 リポジトリ 更新処理 全データ更新処理
	 */

	@Test
	public void updateOk() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		//テストデータ

		String f_datetime = "2022-04-01 00:00:00";

		int facilityId = 0;
		String name = "施設5";
		int capacity = 10;

		int facilityTypeId = 1;
		String ftName = "会議室";
		Date ftInsertDate = null;
		UserEntity ftInsertUserEntity = null;
		Date ftUpdateDate = null;
		UserEntity ftUpdateUserEntity = null;
		Date deleteDate = null;
		UserEntity deleteUserEntity = null;
		boolean fgDelete = false;

		FacilityTypeEntity facilityTypeEntity = new FacilityTypeEntity(facilityTypeId, ftName, ftInsertDate,
				ftInsertUserEntity,
				ftUpdateDate, ftUpdateUserEntity, deleteDate, deleteUserEntity, fgDelete);
		Timestamp insertDate = Timestamp.valueOf(f_datetime);
		UserEntity insertUserEntity = null;
		Timestamp updateDate = Timestamp.valueOf(f_datetime);
		UserEntity updateUserEntity = null;

		FacilityEntity facilityEntity = new FacilityEntity(facilityId, name, capacity, facilityTypeEntity,
				insertDate, insertUserEntity, updateDate, updateUserEntity);

		int result = facilityRepository.update(facilityEntity);

		assertEquals(1, result);
	}

	/**
	 * 施設情報 リポジトリ 更新処理 全データ更新処理 施設名異常　２１文字
	 */

	@Test
	public void updateNg() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);

			PostgreDataManager.dataSet("./data/infra/repository/facilitytype/setup_dataset_facilitytype.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		//テストデータ

		String f_datetime = "2022-04-01 00:00:00";

		int facilityId = 0;
		String name = "施設5555555555555555555";
		int capacity = 10;

		int facilityTypeId = 1;
		String ftName = "会議室";
		Date ftInsertDate = null;
		UserEntity ftInsertUserEntity = null;
		Date ftUpdateDate = null;
		UserEntity ftUpdateUserEntity = null;
		Date deleteDate = null;
		UserEntity deleteUserEntity = null;
		boolean fgDelete = false;

		FacilityTypeEntity facilityTypeEntity = new FacilityTypeEntity(facilityTypeId, ftName, ftInsertDate,
				ftInsertUserEntity,
				ftUpdateDate, ftUpdateUserEntity, deleteDate, deleteUserEntity, fgDelete);
		Timestamp insertDate = Timestamp.valueOf(f_datetime);
		UserEntity insertUserEntity = null;
		Timestamp updateDate = Timestamp.valueOf(f_datetime);
		UserEntity updateUserEntity = null;

		FacilityEntity facilityEntity = new FacilityEntity(facilityId, name, capacity, facilityTypeEntity,
				insertDate, insertUserEntity, updateDate, updateUserEntity);

		try {
			// テスト実施メソッド
			facilityRepository.update(facilityEntity);
			fail("例外発生なし");
		} catch (Exception e) {
			// スキーマ違反例外を期待
			assertThat(e, is(instanceOf(DataIntegrityViolationException.class)));
		}
	}

	/**
	 * 施設情報 リポジトリ 更新処理 データなし
	 */

	@Test
	public void updateNo() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/util/setup_nodata.xml", DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		//テストデータ

		String f_datetime = "2022-04-01 00:00:00";

		int facilityId = 0;
		String name = "施設5";
		int capacity = 10;

		int facilityTypeId = 1;
		String ftName = "会議室";
		Date ftInsertDate = null;
		UserEntity ftInsertUserEntity = null;
		Date ftUpdateDate = null;
		UserEntity ftUpdateUserEntity = null;
		Date deleteDate = null;
		UserEntity deleteUserEntity = null;
		boolean fgDelete = false;

		FacilityTypeEntity facilityTypeEntity = new FacilityTypeEntity(facilityTypeId, ftName, ftInsertDate,
				ftInsertUserEntity,
				ftUpdateDate, ftUpdateUserEntity, deleteDate, deleteUserEntity, fgDelete);
		Timestamp insertDate = Timestamp.valueOf(f_datetime);
		UserEntity insertUserEntity = null;
		Timestamp updateDate = Timestamp.valueOf(f_datetime);
		UserEntity updateUserEntity = null;

		FacilityEntity facilityEntity = new FacilityEntity(facilityId, name, capacity, facilityTypeEntity,
				insertDate, insertUserEntity, updateDate, updateUserEntity);

		int result = facilityRepository.update(facilityEntity);

		assertEquals(0, result);
	}

	/**
	 * 施設情報 リポジトリ 削除処理
	 */

	@Test
	public void deleteOk() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/facility/setup_dataset_facility.xml",
					DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		int result = facilityRepository.delete(0);

		assertEquals(1, result);

	}

	/**
	 * ユーザー情報 リポジトリ 削除処理 データなし
	 */

	@Test
	public void deleteNg() {

		try {
			PostgreDataManager.dataSet("./data/infra/repository/util/setup_nodata.xml", DatabaseOperation.CLEAN_INSERT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("データセット失敗");
			fail();
		}

		int result = facilityRepository.delete(0);

		assertEquals(0, result);

	}
}