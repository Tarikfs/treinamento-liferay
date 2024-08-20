/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.training.gradebook.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.training.gradebook.exception.NoSuchGradebookException;
import com.liferay.training.gradebook.model.Gradebook;
import com.liferay.training.gradebook.service.GradebookLocalServiceUtil;
import com.liferay.training.gradebook.service.persistence.GradebookPersistence;
import com.liferay.training.gradebook.service.persistence.GradebookUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class GradebookPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.training.gradebook.service"));

	@Before
	public void setUp() {
		_persistence = GradebookUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Gradebook> iterator = _gradebooks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Gradebook gradebook = _persistence.create(pk);

		Assert.assertNotNull(gradebook);

		Assert.assertEquals(gradebook.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Gradebook newGradebook = addGradebook();

		_persistence.remove(newGradebook);

		Gradebook existingGradebook = _persistence.fetchByPrimaryKey(
			newGradebook.getPrimaryKey());

		Assert.assertNull(existingGradebook);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addGradebook();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Gradebook newGradebook = _persistence.create(pk);

		newGradebook.setUuid(RandomTestUtil.randomString());

		newGradebook.setGroupId(RandomTestUtil.nextLong());

		newGradebook.setCompanyId(RandomTestUtil.nextLong());

		newGradebook.setUserId(RandomTestUtil.nextLong());

		newGradebook.setUserName(RandomTestUtil.randomString());

		newGradebook.setCreateDate(RandomTestUtil.nextDate());

		newGradebook.setModifiedDate(RandomTestUtil.nextDate());

		newGradebook.setTitle(RandomTestUtil.randomString());

		newGradebook.setDescription(RandomTestUtil.randomString());

		newGradebook.setDueDate(RandomTestUtil.nextDate());

		_gradebooks.add(_persistence.update(newGradebook));

		Gradebook existingGradebook = _persistence.findByPrimaryKey(
			newGradebook.getPrimaryKey());

		Assert.assertEquals(
			existingGradebook.getUuid(), newGradebook.getUuid());
		Assert.assertEquals(
			existingGradebook.getAssignmentId(),
			newGradebook.getAssignmentId());
		Assert.assertEquals(
			existingGradebook.getGroupId(), newGradebook.getGroupId());
		Assert.assertEquals(
			existingGradebook.getCompanyId(), newGradebook.getCompanyId());
		Assert.assertEquals(
			existingGradebook.getUserId(), newGradebook.getUserId());
		Assert.assertEquals(
			existingGradebook.getUserName(), newGradebook.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingGradebook.getCreateDate()),
			Time.getShortTimestamp(newGradebook.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingGradebook.getModifiedDate()),
			Time.getShortTimestamp(newGradebook.getModifiedDate()));
		Assert.assertEquals(
			existingGradebook.getTitle(), newGradebook.getTitle());
		Assert.assertEquals(
			existingGradebook.getDescription(), newGradebook.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(existingGradebook.getDueDate()),
			Time.getShortTimestamp(newGradebook.getDueDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCollection() throws Exception {
		_persistence.countByCollection(RandomTestUtil.nextLong());

		_persistence.countByCollection(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Gradebook newGradebook = addGradebook();

		Gradebook existingGradebook = _persistence.findByPrimaryKey(
			newGradebook.getPrimaryKey());

		Assert.assertEquals(existingGradebook, newGradebook);
	}

	@Test(expected = NoSuchGradebookException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Gradebook> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Gradebook_Gradebook", "uuid", true, "assignmentId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "title", true,
			"description", true, "dueDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Gradebook newGradebook = addGradebook();

		Gradebook existingGradebook = _persistence.fetchByPrimaryKey(
			newGradebook.getPrimaryKey());

		Assert.assertEquals(existingGradebook, newGradebook);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Gradebook missingGradebook = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingGradebook);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Gradebook newGradebook1 = addGradebook();
		Gradebook newGradebook2 = addGradebook();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGradebook1.getPrimaryKey());
		primaryKeys.add(newGradebook2.getPrimaryKey());

		Map<Serializable, Gradebook> gradebooks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, gradebooks.size());
		Assert.assertEquals(
			newGradebook1, gradebooks.get(newGradebook1.getPrimaryKey()));
		Assert.assertEquals(
			newGradebook2, gradebooks.get(newGradebook2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Gradebook> gradebooks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(gradebooks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Gradebook newGradebook = addGradebook();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGradebook.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Gradebook> gradebooks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, gradebooks.size());
		Assert.assertEquals(
			newGradebook, gradebooks.get(newGradebook.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Gradebook> gradebooks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(gradebooks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Gradebook newGradebook = addGradebook();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGradebook.getPrimaryKey());

		Map<Serializable, Gradebook> gradebooks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, gradebooks.size());
		Assert.assertEquals(
			newGradebook, gradebooks.get(newGradebook.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			GradebookLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Gradebook>() {

				@Override
				public void performAction(Gradebook gradebook) {
					Assert.assertNotNull(gradebook);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Gradebook newGradebook = addGradebook();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Gradebook.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", newGradebook.getAssignmentId()));

		List<Gradebook> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		Gradebook existingGradebook = result.get(0);

		Assert.assertEquals(existingGradebook, newGradebook);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Gradebook.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", RandomTestUtil.nextLong()));

		List<Gradebook> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Gradebook newGradebook = addGradebook();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Gradebook.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assignmentId"));

		Object newAssignmentId = newGradebook.getAssignmentId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assignmentId", new Object[] {newAssignmentId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssignmentId = result.get(0);

		Assert.assertEquals(existingAssignmentId, newAssignmentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Gradebook.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assignmentId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assignmentId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Gradebook newGradebook = addGradebook();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newGradebook.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		Gradebook newGradebook = addGradebook();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Gradebook.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", newGradebook.getAssignmentId()));

		List<Gradebook> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(Gradebook gradebook) {
		Assert.assertEquals(
			gradebook.getUuid(),
			ReflectionTestUtil.invoke(
				gradebook, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(gradebook.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				gradebook, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(gradebook.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				gradebook, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected Gradebook addGradebook() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Gradebook gradebook = _persistence.create(pk);

		gradebook.setUuid(RandomTestUtil.randomString());

		gradebook.setGroupId(RandomTestUtil.nextLong());

		gradebook.setCompanyId(RandomTestUtil.nextLong());

		gradebook.setUserId(RandomTestUtil.nextLong());

		gradebook.setUserName(RandomTestUtil.randomString());

		gradebook.setCreateDate(RandomTestUtil.nextDate());

		gradebook.setModifiedDate(RandomTestUtil.nextDate());

		gradebook.setTitle(RandomTestUtil.randomString());

		gradebook.setDescription(RandomTestUtil.randomString());

		gradebook.setDueDate(RandomTestUtil.nextDate());

		_gradebooks.add(_persistence.update(gradebook));

		return gradebook;
	}

	private List<Gradebook> _gradebooks = new ArrayList<Gradebook>();
	private GradebookPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}