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

package org.springframework.transaction;

import org.springframework.lang.Nullable;

/**
 * Interface that defines Spring-compliant transaction properties. Based on the propagation behavior
 * definitions analogous to EJB CMT attributes.
 *
 * <p>Note that isolation level and timeout settings will not get applied unless
 * an actual new transaction gets started. As only {@link #PROPAGATION_REQUIRED}, {@link
 * #PROPAGATION_REQUIRES_NEW} and {@link #PROPAGATION_NESTED} can cause that, it usually doesn't
 * make sense to specify those settings in other cases. Furthermore, be aware that not all
 * transaction managers will support those advanced features and thus might throw corresponding
 * exceptions when given non-default values.
 *
 * <p>The {@link #isReadOnly() read-only flag} applies to any transaction context,
 * whether backed by an actual resource transaction or operating non-transactionally at the resource
 * level. In the latter case, the flag will only apply to managed resources within the application,
 * such as a Hibernate {@code Session}.
 *
 * @author Juergen Hoeller
 * @see PlatformTransactionManager#getTransaction(TransactionDefinition)
 * @see org.springframework.transaction.support.DefaultTransactionDefinition
 * @see org.springframework.transaction.interceptor.TransactionAttribute
 * @since 08.05.2003
 *
 * 定义事物处理属性(隔离级别、传播行为、只读、超时)，不推荐使用，会造成事物处理代码和业务代码紧密耦合。
 */
public interface TransactionDefinition {

	/**
	 * Support a current transaction; create a new one if none exists. Analogous to the EJB
	 * transaction attribute of the same name.
	 * <p>This is typically the default setting of a transaction definition,
	 * and typically defines a transaction synchronization scope.
	 *
	 * 当前有事物则加入，没有则新建。
	 *
	 * 只有一个事务，完全回滚或提交。
	 */
	int PROPAGATION_REQUIRED = 0;

	/**
	 * Support a current transaction; execute non-transactionally if none exists. Analogous to the
	 * EJB transaction attribute of the same name.
	 * <p><b>NOTE:</b> For transaction managers with transaction synchronization,
	 * {@code PROPAGATION_SUPPORTS} is slightly different from no transaction at all, as it defines
	 * a transaction scope that synchronization might apply to. As a consequence, the same resources
	 * (a JDBC {@code Connection}, a Hibernate {@code Session}, etc) will be shared for the entire
	 * specified scope. Note that the exact behavior depends on the actual synchronization
	 * configuration of the transaction manager!
	 * <p>In general, use {@code PROPAGATION_SUPPORTS} with care! In particular, do
	 * not rely on {@code PROPAGATION_REQUIRED} or {@code PROPAGATION_REQUIRES_NEW}
	 * <i>within</i> a {@code PROPAGATION_SUPPORTS} scope (which may lead to
	 * synchronization conflicts at runtime). If such nesting is unavoidable, make sure to configure
	 * your transaction manager appropriately (typically switching to "synchronization on actual
	 * transaction").
	 *
	 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#setTransactionSynchronization
	 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#SYNCHRONIZATION_ON_ACTUAL_TRANSACTION
	 *
	 * 当前有事物则加入，没有则直接运行。
	 */
	int PROPAGATION_SUPPORTS = 1;

	/**
	 * Support a current transaction; throw an exception if no current transaction exists. Analogous
	 * to the EJB transaction attribute of the same name.
	 * <p>Note that transaction synchronization within a {@code PROPAGATION_MANDATORY}
	 * scope will always be driven by the surrounding transaction.
	 *
	 * 当前没有事物则抛出异常。
	 */
	int PROPAGATION_MANDATORY = 2;

	/**
	 * Create a new transaction, suspending the current transaction if one exists. Analogous to the
	 * EJB transaction attribute of the same name.
	 * <p><b>NOTE:</b> Actual transaction suspension will not work out-of-the-box
	 * on all transaction managers. This in particular applies to {@link
	 * org.springframework.transaction.jta.JtaTransactionManager}, which requires the {@code
	 * javax.transaction.TransactionManager} to be made available it to it (which is server-specific
	 * in standard Java EE).
	 * <p>A {@code PROPAGATION_REQUIRES_NEW} scope always defines its own
	 * transaction synchronizations. Existing synchronizations will be suspended and resumed
	 * appropriately.
	 *
	 * @see org.springframework.transaction.jta.JtaTransactionManager#setTransactionManager
	 *
	 * 不管当前是否存在事物，都新建一个事物。对于已有的事物，挂起。
	 *
	 * 挂起
	 * 		事物不应该在外部事物中运行，内部事务开启将暂停外部事物，直到内部事物完成。
	 *
	 * 暂停
	 * 		暂时不用于插入、更新、提交或回滚的事务，因为由于指定的传播属性应创建一个新事务，并且只能激活一个事务同时。
	 *
	 * 适用于当前事物不会影响外层事物，如：日志信息。
	 *
	 * 外部事物回滚，不会引起内部事物回滚。
	 * 不捕获内部事物情况下，内部事物先提交（回滚），外部事物后提交（回滚）。
	 */
	int PROPAGATION_REQUIRES_NEW = 3;

	/**
	 * Do not support a current transaction; rather always execute non-transactionally. Analogous to
	 * the EJB transaction attribute of the same name.
	 * <p><b>NOTE:</b> Actual transaction suspension will not work out-of-the-box
	 * on all transaction managers. This in particular applies to {@link
	 * org.springframework.transaction.jta.JtaTransactionManager}, which requires the {@code
	 * javax.transaction.TransactionManager} to be made available it to it (which is server-specific
	 * in standard Java EE).
	 * <p>Note that transaction synchronization is <i>not</i> available within a
	 * {@code PROPAGATION_NOT_SUPPORTED} scope. Existing synchronizations will be suspended and
	 * resumed appropriately.
	 *
	 * @see org.springframework.transaction.jta.JtaTransactionManager#setTransactionManager
	 *
	 * 不支持当前事物，当前存在事物会挂起事物。
	 */
	int PROPAGATION_NOT_SUPPORTED = 4;

	/**
	 * Do not support a current transaction; throw an exception if a current transaction exists.
	 * Analogous to the EJB transaction attribute of the same name.
	 * <p>Note that transaction synchronization is <i>not</i> available within a
	 * {@code PROPAGATION_NEVER} scope.
	 *
	 * 有事物抛出异常。
	 */
	int PROPAGATION_NEVER = 5;

	/**
	 * Execute within a nested transaction if a current transaction exists, behave like {@link
	 * #PROPAGATION_REQUIRED} otherwise. There is no analogous feature in EJB.
	 * <p><b>NOTE:</b> Actual creation of a nested transaction will only work on
	 * specific transaction managers. Out of the box, this only applies to the JDBC {@link
	 * org.springframework.jdbc.datasource.DataSourceTransactionManager} when working on a JDBC 3.0
	 * driver. Some JTA providers might support nested transactions as well.
	 *
	 * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
	 *
	 * 存在事物，在当前事物嵌套事物中执行。
	 *
	 * 嵌套事务是外部事务的一部分（现有事务的真正子事务），因此它只会在外部事务结束时提交。
	 *
	 * 在嵌套事务开始时将采用保存点。如果嵌套事务失败，我们将回滚到该保存点。
	 *
	 * 内部事物和外部事物，要么一起回滚，要么一起提交（内部事物异常不捕获）。
	 */
	int PROPAGATION_NESTED = 6;


	/**
	 * Use the default isolation level of the underlying datastore. All other levels correspond to the JDBC isolation levels.
	 *
	 * 使用数据库默认隔离级别。
	 *
	 * @see java.sql.Connection
	 */
	int ISOLATION_DEFAULT = -1;

	/**
	 * Indicates that dirty reads, non-repeatable reads and phantom reads can occur.
	 * <p>This level allows a row changed by one transaction to be read by another
	 * transaction before any changes in that row have been committed (a "dirty read"). If any of
	 * the changes are rolled back, the second transaction will have retrieved an invalid row.
	 *
	 * 读未提交，存在脏读、不可重复读、幻读。
	 *
	 * @see java.sql.Connection#TRANSACTION_READ_UNCOMMITTED
	 */
	int ISOLATION_READ_UNCOMMITTED = 1;  // same as java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;

	/**
	 * Indicates that dirty reads are prevented; non-repeatable reads and phantom reads can occur.
	 * <p>This level only prohibits a transaction from reading a row
	 * with uncommitted changes in it.
	 *
	 * 读已提交，存在不可重复读、幻读。
	 *
	 * @see java.sql.Connection#TRANSACTION_READ_COMMITTED
	 */
	int ISOLATION_READ_COMMITTED = 2;  // same as java.sql.Connection.TRANSACTION_READ_COMMITTED;

	/**
	 * Indicates that dirty reads and non-repeatable reads are prevented; phantom reads can occur.
	 * <p>This level prohibits a transaction from reading a row with uncommitted changes
	 * in it, and it also prohibits the situation where one transaction reads a row, a second
	 * transaction alters the row, and the first transaction re-reads the row, getting different
	 * values the second time (a "non-repeatable read").
	 *
	 * 可重复读，存在幻读。
	 *
	 * @see java.sql.Connection#TRANSACTION_REPEATABLE_READ
	 */
	int ISOLATION_REPEATABLE_READ = 4;  // same as java.sql.Connection.TRANSACTION_REPEATABLE_READ;

	/**
	 * Indicates that dirty reads, non-repeatable reads and phantom reads are prevented.
	 * <p>This level includes the prohibitions in {@link #ISOLATION_REPEATABLE_READ}
	 * and further prohibits the situation where one transaction reads all rows that satisfy a
	 * {@code WHERE} condition, a second transaction inserts a row that satisfies that {@code WHERE}
	 * condition, and the first transaction re-reads for the same condition, retrieving the
	 * additional "phantom" row in the second read.
	 *
	 * 可串行化。
	 *
	 * @see java.sql.Connection#TRANSACTION_SERIALIZABLE
	 */
	int ISOLATION_SERIALIZABLE = 8;  // same as java.sql.Connection.TRANSACTION_SERIALIZABLE;


	/**
	 * Use the default timeout of the underlying transaction system, or none if timeouts are not
	 * supported.
	 */
	int TIMEOUT_DEFAULT = -1;

	/**
	 * Return an unmodifiable {@code TransactionDefinition} with defaults.
	 * <p>For customization purposes, use the modifiable
	 * {@link org.springframework.transaction.support.DefaultTransactionDefinition} instead.
	 *
	 * 获取默认的不可修改的事务定义对象。
	 * @since 5.2
	 */
	static TransactionDefinition withDefaults() {
		return StaticTransactionDefinition.INSTANCE;
	}

	/**
	 * Return the propagation behavior.
	 * <p>Must return one of the {@code PROPAGATION_XXX} constants
	 * defined on {@link TransactionDefinition this interface}.
	 * <p>The default is {@link #PROPAGATION_REQUIRED}.
	 *
	 * 获取事务的传播行为。
	 * @return the propagation behavior
	 * @see #PROPAGATION_REQUIRED
	 * @see org.springframework.transaction.support.TransactionSynchronizationManager#isActualTransactionActive()
	 */
	default int getPropagationBehavior() {
		return PROPAGATION_REQUIRED;
	}

	/**
	 * Return the isolation level.
	 * <p>Must return one of the {@code ISOLATION_XXX} constants defined on
	 * {@link TransactionDefinition this interface}. Those constants are designed to match the
	 * values of the same constants on {@link java.sql.Connection}.
	 * <p>Exclusively designed for use with {@link #PROPAGATION_REQUIRED} or
	 * {@link #PROPAGATION_REQUIRES_NEW} since it only applies to newly started transactions.
	 * Consider switching the "validateExistingTransactions" flag to "true" on your transaction
	 * manager if you'd like isolation level declarations to get rejected when participating in an
	 * existing transaction with a different isolation level.
	 * <p>The default is {@link #ISOLATION_DEFAULT}. Note that a transaction manager
	 * that does not support custom isolation levels will throw an exception when given any other
	 * level than {@link #ISOLATION_DEFAULT}.
	 *
	 * 事务的隔离级别
	 * @return the isolation level
	 * @see #ISOLATION_DEFAULT
	 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#setValidateExistingTransaction
	 */
	default int getIsolationLevel() {
		return ISOLATION_DEFAULT;
	}

	/**
	 * Return the transaction timeout.
	 * <p>Must return a number of seconds, or {@link #TIMEOUT_DEFAULT}.
	 * <p>Exclusively designed for use with {@link #PROPAGATION_REQUIRED} or
	 * {@link #PROPAGATION_REQUIRES_NEW} since it only applies to newly started transactions.
	 * <p>Note that a transaction manager that does not support timeouts will throw
	 * an exception when given any other timeout than {@link #TIMEOUT_DEFAULT}.
	 * <p>The default is {@link #TIMEOUT_DEFAULT}.
	 *
	 * 获取事务处理的超时时间
	 * @return the transaction timeout
	 */
	default int getTimeout() {
		return TIMEOUT_DEFAULT;
	}

	/**
	 * Return whether to optimize as a read-only transaction.
	 * <p>The read-only flag applies to any transaction context, whether backed
	 * by an actual resource transaction ({@link #PROPAGATION_REQUIRED}/ {@link
	 * #PROPAGATION_REQUIRES_NEW}) or operating non-transactionally at the resource level ({@link
	 * #PROPAGATION_SUPPORTS}). In the latter case, the flag will only apply to managed resources
	 * within the application, such as a Hibernate {@code Session}.
	 * <p>This just serves as a hint for the actual transaction subsystem;
	 * it will <i>not necessarily</i> cause failure of write access attempts. A transaction manager
	 * which cannot interpret the read-only hint will
	 * <i>not</i> throw an exception when asked for a read-only transaction.
	 *
	 * @return {@code true} if the transaction is to be optimized as read-only ({@code false} by
	 * default)
	 * @see org.springframework.transaction.support.TransactionSynchronization#beforeCommit(boolean)
	 * @see org.springframework.transaction.support.TransactionSynchronizationManager#isCurrentTransactionReadOnly()
	 */
	default boolean isReadOnly() {
		return false;
	}

	// Static builder methods

	/**
	 * Return the name of this transaction. Can be {@code null}.
	 * <p>This will be used as the transaction name to be shown in a
	 * transaction monitor, if applicable (for example, WebLogic's).
	 * <p>In case of Spring's declarative transactions, the exposed name will be
	 * the {@code fully-qualified class name + "." + method name} (by default).
	 *
	 * @return the name of this transaction ({@code null} by default}
	 * @see org.springframework.transaction.interceptor.TransactionAspectSupport
	 * @see org.springframework.transaction.support.TransactionSynchronizationManager#getCurrentTransactionName()
	 */
	@Nullable
	default String getName() {
		return null;
	}

}
