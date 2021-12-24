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

/**
 * Marker interface for Spring transaction manager implementations,
 * either traditional or reactive.
 *
 * 是使用AOP实现声明式处理的拦截器，封装了Spring对声明式事物处理实现的基本过程。
 *
 * 为事物的实现设计好了一系列模板方法，在准备完事物有关的数据之后，提供事物处理（事物提交、回滚等），封装事物处理设置以及与特定数据源相关的特定事物处理过程。
 *
 * 优点：做到使用者对事物处理的开箱即用。
 *
 * @author Juergen Hoeller
 * @since 5.2
 * @see PlatformTransactionManager
 * @see ReactiveTransactionManager
 */
public interface TransactionManager {

}
