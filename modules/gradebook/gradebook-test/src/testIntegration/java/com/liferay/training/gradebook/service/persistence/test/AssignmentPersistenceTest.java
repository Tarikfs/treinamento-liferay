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
import com.liferay.training.gradebook.exception.NoSuchAssignmentException;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentLocalServiceUtil;
import com.liferay.training.gradebook.service.persistence.AssignmentPersistence;
import com.liferay.training.gradebook.service.persistence.AssignmentUtil;

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
public class AssignmentPersistenceTest {

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
		_persistence = AssignmentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Assignment> iterator = _assignments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Assignment assignment = _persistence.create(pk);

		Assert.assertNotNull(assignment);

		Assert.assertEquals(assignment.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Assignment newAssignment = addAssignment();

		_persistence.remove(newAssignment);

		Assignment existingAssignment = _persistence.fetchByPrimaryKey(
			newAssignment.getPrimaryKey());

		Assert.assertNull(existingAssignment);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssignment();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Assignment newAssignment = _persistence.create(pk);

		newAssignment.setUuid(RandomTestUtil.randomString());

		newAssignment.setGroupId(RandomTestUtil.nextLong());

		newAssignment.setCompanyId(RandomTestUtil.nextLong());

		newAssignment.setUserId(RandomTestUtil.nextLong());

		newAssignment.setUserName(RandomTestUtil.randomString());

		newAssignment.setCreateDate(RandomTestUtil.nextDate());

		newAssignment.setModifiedDate(RandomTestUtil.nextDate());

		newAssignment.setTitle(RandomTestUtil.randomString());

		newAssignment.setDescription(RandomTestUtil.randomString());

		newAssignment.setDueDate(RandomTestUtil.nextDate());

		_assignments.add(_persistence.update(newAssignment));

		Assignment existingAssignment = _persistence.findByPrimaryKey(
			newAssignment.getPrimaryKey());

		Assert.assertEquals(
			existingAssignment.getUuid(), newAssignment.getUuid());
		Assert.assertEquals(
			existingAssignment.getAssignmentId(),
			newAssignment.getAssignmentId());
		Assert.assertEquals(
			existingAssignment.getGroupId(), newAssignment.getGroupId());
		Assert.assertEquals(
			existingAssignment.getCompanyId(), newAssignment.getCompanyId());
		Assert.assertEquals(
			existingAssignment.getUserId(), newAssignment.getUserId());
		Assert.assertEquals(
			existingAssignment.getUserName(), newAssignment.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssignment.getCreateDate()),
			Time.getShortTimestamp(newAssignment.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssignment.getModifiedDate()),
			Time.getShortTimestamp(newAssignment.getModifiedDate()));
		Assert.assertEquals(
			existingAssignment.getTitle(), newAssignment.getTitle());
		Assert.assertEquals(
			existingAssignment.getDescription(),
			newAssignment.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssignment.getDueDate()),
			Time.getShortTimestamp(newAssignment.getDueDate()));
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
		Assignment newAssignment = addAssignment();

		Assignment existingAssignment = _persistence.findByPrimaryKey(
			newAssignment.getPrimaryKey());

		Assert.assertEquals(existingAssignment, newAssignment);
	}

	@Test(expected = NoSuchAssignmentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Assignment> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Gradebook_Assignment", "uuid", true, "assignmentId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "title", true,
			"description", true, "dueDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Assignment newAssignment = addAssignment();

		Assignment existingAssignment = _persistence.fetchByPrimaryKey(
			newAssignment.getPrimaryKey());

		Assert.assertEquals(existingAssignment, newAssignment);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Assignment missingAssignment = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssignment);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Assignment newAssignment1 = addAssignment();
		Assignment newAssignment2 = addAssignment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssignment1.getPrimaryKey());
		primaryKeys.add(newAssignment2.getPrimaryKey());

		Map<Serializable, Assignment> assignments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assignments.size());
		Assert.assertEquals(
			newAssignment1, assignments.get(newAssignment1.getPrimaryKey()));
		Assert.assertEquals(
			newAssignment2, assignments.get(newAssignment2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Assignment> assignments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assignments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Assignment newAssignment = addAssignment();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssignment.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Assignment> assignments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assignments.size());
		Assert.assertEquals(
			newAssignment, assignments.get(newAssignment.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Assignment> assignments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assignments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Assignment newAssignment = addAssignment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssignment.getPrimaryKey());

		Map<Serializable, Assignment> assignments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assignments.size());
		Assert.assertEquals(
			newAssignment, assignments.get(newAssignment.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssignmentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Assignment>() {

				@Override
				public void performAction(Assignment assignment) {
					Assert.assertNotNull(assignment);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Assignment newAssignment = addAssignment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Assignment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", newAssignment.getAssignmentId()));

		List<Assignment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		Assignment existingAssignment = result.get(0);

		Assert.assertEquals(existingAssignment, newAssignment);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Assignment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", RandomTestUtil.nextLong()));

		List<Assignment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Assignment newAssignment = addAssignment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Assignment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assignmentId"));

		Object newAssignmentId = newAssignment.getAssignmentId();

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
			Assignment.class, _dynamicQueryClassLoader);

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
		Assignment newAssignment = addAssignment();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newAssignment.getPrimaryKey()));
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

		Assignment newAssignment = addAssignment();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Assignment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assignmentId", newAssignment.getAssignmentId()));

		List<Assignment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(Assignment assignment) {
		Assert.assertEquals(
			assignment.getUuid(),
			ReflectionTestUtil.invoke(
				assignment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(assignment.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				assignment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(assignment.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				assignment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected Assignment addAssignment() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Assignment assignment = _persistence.create(pk);

		assignment.setUuid(RandomTestUtil.randomString());

		assignment.setGroupId(RandomTestUtil.nextLong());

		assignment.setCompanyId(RandomTestUtil.nextLong());

		assignment.setUserId(RandomTestUtil.nextLong());

		assignment.setUserName(RandomTestUtil.randomString());

		assignment.setCreateDate(RandomTestUtil.nextDate());

		assignment.setModifiedDate(RandomTestUtil.nextDate());

		assignment.setTitle(RandomTestUtil.randomString());

		assignment.setDescription(RandomTestUtil.randomString());

		assignment.setDueDate(RandomTestUtil.nextDate());

		_assignments.add(_persistence.update(assignment));

		return assignment;
	}

	private List<Assignment> _assignments = new ArrayList<Assignment>();
	private AssignmentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}