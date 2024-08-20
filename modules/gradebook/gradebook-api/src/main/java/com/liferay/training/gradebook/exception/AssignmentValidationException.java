/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.training.gradebook.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignmentValidationException extends PortalException {

	public AssignmentValidationException() {
	}

	public AssignmentValidationException(String msg) {
		super(msg);
	}

	public AssignmentValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AssignmentValidationException(Throwable throwable) {
		super(throwable);
	}

}