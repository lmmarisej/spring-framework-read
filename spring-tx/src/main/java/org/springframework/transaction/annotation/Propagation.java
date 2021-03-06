/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction.annotation;

import org.springframework.transaction.TransactionDefinition;

/**
 * Enumeration that represents transaction propagation behaviors for use with the {@link
 * Transactional} annotation, corresponding to the {@link TransactionDefinition} interface.
 *
 * @author Colin Sampaleanu
 * @author Juergen Hoeller
 * @since 1.2
 */
public enum Propagation {		// 事务传播行为
	
	/**
	 * Support a current transaction, create a new one if none exists. Analogous to EJB transaction
	 * attribute of the same name.
	 * <p>This is the default setting of a transaction annotation.
	 */
	REQUIRED(TransactionDefinition.PROPAGATION_REQUIRED),        // 有事务则加入，没有则新建
	
	/**
	 * Support a current transaction, execute non-transactionally if none exists. Analogous to EJB
	 * transaction attribute of the same name.
	 * <p>Note: For transaction managers with transaction synchronization,
	 * {@code SUPPORTS} is slightly different from no transaction at all, as it defines a
	 * transaction scope that synchronization will apply for. As a consequence, the same resources
	 * (JDBC Connection, Hibernate Session, etc) will be shared for the entire specified scope. Note
	 * that this depends on the actual synchronization configuration of the transaction manager.
	 *
	 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#setTransactionSynchronization
	 */
	SUPPORTS(TransactionDefinition.PROPAGATION_SUPPORTS),        // 有事务就用，如果没有就不开启(继承关系)
	
	/**
	 * Support a current transaction, throw an exception if none exists. Analogous to EJB
	 * transaction attribute of the same name.
	 */
	MANDATORY(TransactionDefinition.PROPAGATION_MANDATORY),        // 必须在已有事务中
	
	/**
	 * Create a new transaction, and suspend the current transaction if one exists. Analogous to the
	 * EJB transaction attribute of the same name.
	 * <p><b>NOTE:</b> Actual transaction suspension will not work out-of-the-box
	 * on all transaction managers. This in particular applies to {@link
	 * org.springframework.transaction.jta.JtaTransactionManager}, which requires the {@code
	 * javax.transaction.TransactionManager} to be made available to it (which is server-specific in
	 * standard Java EE).
	 *
	 * @see org.springframework.transaction.jta.JtaTransactionManager#setTransactionManager
	 */
	REQUIRES_NEW(TransactionDefinition.PROPAGATION_REQUIRES_NEW),        // 不管是否已有事务，都要开启新事务，老事务挂起
	
	/**
	 * Execute non-transactionally, suspend the current transaction if one exists. Analogous to EJB
	 * transaction attribute of the same name.
	 * <p><b>NOTE:</b> Actual transaction suspension will not work out-of-the-box
	 * on all transaction managers. This in particular applies to {@link
	 * org.springframework.transaction.jta.JtaTransactionManager}, which requires the {@code
	 * javax.transaction.TransactionManager} to be made available to it (which is server-specific in
	 * standard Java EE).
	 *
	 * @see org.springframework.transaction.jta.JtaTransactionManager#setTransactionManager
	 */
	NOT_SUPPORTED(TransactionDefinition.PROPAGATION_NOT_SUPPORTED),        // 不开启事务
	
	/**
	 * Execute non-transactionally, throw an exception if a transaction exists. Analogous to EJB
	 * transaction attribute of the same name.
	 */
	NEVER(TransactionDefinition.PROPAGATION_NEVER),        // 必须在没有事务的方法中调用，否则抛出异常
	
	/**
	 * Execute within a nested transaction if a current transaction exists, behave like {@code
	 * REQUIRED} otherwise. There is no analogous feature in EJB.
	 * <p>Note: Actual creation of a nested transaction will only work on specific
	 * transaction managers. Out of the box, this only applies to the JDBC
	 * DataSourceTransactionManager. Some JTA providers might support nested transactions as well.
	 *
	 * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
	 */
	NESTED(TransactionDefinition.PROPAGATION_NESTED);        // 如果已有事务则创建保存点，如果没有就新建事务
	
	
	private final int value;
	
	
	Propagation(int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
	
}
