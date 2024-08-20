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
import com.liferay.training.gradebook.exception.NoSuchFooException;
import com.liferay.training.gradebook.model.Foo;
import com.liferay.training.gradebook.service.FooLocalServiceUtil;
import com.liferay.training.gradebook.service.persistence.FooPersistence;
import com.liferay.training.gradebook.service.persistence.FooUtil;

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
public class FooPersistenceTest {

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
		_persistence = FooUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Foo> iterator = _foos.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Foo foo = _persistence.create(pk);

		Assert.assertNotNull(foo);

		Assert.assertEquals(foo.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Foo newFoo = addFoo();

		_persistence.remove(newFoo);

		Foo existingFoo = _persistence.fetchByPrimaryKey(
			newFoo.getPrimaryKey());

		Assert.assertNull(existingFoo);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFoo();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Foo newFoo = _persistence.create(pk);

		newFoo.setUuid(RandomTestUtil.randomString());

		newFoo.setGroupId(RandomTestUtil.nextLong());

		newFoo.setCompanyId(RandomTestUtil.nextLong());

		newFoo.setUserId(RandomTestUtil.nextLong());

		newFoo.setUserName(RandomTestUtil.randomString());

		newFoo.setCreateDate(RandomTestUtil.nextDate());

		newFoo.setModifiedDate(RandomTestUtil.nextDate());

		newFoo.setTitle(RandomTestUtil.randomString());

		newFoo.setDescription(RandomTestUtil.randomString());

		newFoo.setDueDate(RandomTestUtil.nextDate());

		_foos.add(_persistence.update(newFoo));

		Foo existingFoo = _persistence.findByPrimaryKey(newFoo.getPrimaryKey());

		Assert.assertEquals(existingFoo.getUuid(), newFoo.getUuid());
		Assert.assertEquals(
			existingFoo.getAssignmentId(), newFoo.getAssignmentId());
		Assert.assertEquals(existingFoo.getGroupId(), newFoo.getGroupId());
		Assert.assertEquals(existingFoo.getCompanyId(), newFoo.getCompanyId());
		Assert.assertEquals(existingFoo.getUserId(), newFoo.getUserId());
		Assert.assertEquals(existingFoo.getUserName(), newFoo.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFoo.getCreateDate()),
			Time.getShortTimestamp(newFoo.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingFoo.getModifiedDate()),
			Time.getShortTimestamp(newFoo.getModifiedDate()));
		Assert.assertEquals(existingFoo.getTitle(), newFoo.getTitle());
		Assert.assertEquals(
			existingFoo.getDescription(), newFoo.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFoo.getDueDate()),
			Time.getShortTimestamp(newFoo.getDueDate()));
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
		Foo newFoo = addFoo();

		Foo existingFoo = _persistence.findByPrimaryKey(newFoo.getPrimaryKey());

		Assert.assertEquals(existingFoo, newFoo);
	}

	@Test(expected = NoSuchFooException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Foo> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FOO_Foo", "uuid", true, "assignmentId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "title", true, "description", true,
			"dueDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Foo newFoo = addFoo();

		Foo existingFoo = _persistence.fetchByPrimaryKey(
			newFoo.getPrimaryKey());

		Assert.assertEquals(existingFoo, newFoo);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Foo missingFoo = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFoo);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Foo newFoo1 = addFoo();
		Foo newFoo2 = addFoo();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFoo1.getPrimaryKey());
		primaryKeys.add(newFoo2.getPrimaryKey());

		Map<Serializable, Foo> foos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, foos.size());
		Assert.assertEquals(newFoo1, foos.get(newFoo1.getPrimaryKey()));
		Assert.assertEquals(newFoo2, foos.get(newFoo2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Foo> foos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(foos.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Foo newFoo = addFoo();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFoo.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Foo> foos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, foos.size());
		Assert.assertEquals(newFoo, foos.get(newFoo.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Foo> foos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(foos.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Foo newFoo = addFoo();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFoo.getPrimaryKey());

		Map<Serializable, Foo> foos = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, foos.size());
		Assert.assertEquals(newFoo, foos.get(newFoo.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FooLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Foo>() {

				@Override
				public void performAction(Foo foo) {
					Assert.assertNotNull(foo);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Foo newFoo = addFoo();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Foo.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", newFoo.getAssignmentId()));

		List<Foo> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Foo existingFoo = result.get(0);

		Assert.assertEquals(existingFoo, newFoo);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Foo.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", RandomTestUtil.nextLong()));

		List<Foo> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Foo newFoo = addFoo();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Foo.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assignmentId"));

		Object newAssignmentId = newFoo.getAssignmentId();

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
			Foo.class, _dynamicQueryClassLoader);

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
		Foo newFoo = addFoo();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newFoo.getPrimaryKey()));
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

		Foo newFoo = addFoo();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Foo.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", newFoo.getAssignmentId()));

		List<Foo> result = _persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(Foo foo) {
		Assert.assertEquals(
			foo.getUuid(),
			ReflectionTestUtil.invoke(
				foo, "getColumnOriginalValue", new Class<?>[] {String.class},
				"uuid_"));
		Assert.assertEquals(
			Long.valueOf(foo.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				foo, "getColumnOriginalValue", new Class<?>[] {String.class},
				"groupId"));

		Assert.assertEquals(
			Long.valueOf(foo.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				foo, "getColumnOriginalValue", new Class<?>[] {String.class},
				"groupId"));
	}

	protected Foo addFoo() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Foo foo = _persistence.create(pk);

		foo.setUuid(RandomTestUtil.randomString());

		foo.setGroupId(RandomTestUtil.nextLong());

		foo.setCompanyId(RandomTestUtil.nextLong());

		foo.setUserId(RandomTestUtil.nextLong());

		foo.setUserName(RandomTestUtil.randomString());

		foo.setCreateDate(RandomTestUtil.nextDate());

		foo.setModifiedDate(RandomTestUtil.nextDate());

		foo.setTitle(RandomTestUtil.randomString());

		foo.setDescription(RandomTestUtil.randomString());

		foo.setDueDate(RandomTestUtil.nextDate());

		_foos.add(_persistence.update(foo));

		return foo;
	}

	private List<Foo> _foos = new ArrayList<Foo>();
	private FooPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}