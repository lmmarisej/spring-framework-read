/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.aop.framework.adapter;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;

/**
 * Interface allowing extension to the Spring AOP framework to allow
 * handling of new Advisors and Advice types.
 *
 * <p>Implementing objects can create AOP Alliance Interceptors from
 * custom advice types, enabling these advice types to be used
 * in the Spring AOP framework, which uses interception under the covers.
 *
 * <p>There is no need for most Spring users to implement this interface;
 * do so only if you need to introduce more Advisor or Advice types to Spring.
 *
 * @author Rod Johnson
 */
public interface AdvisorAdapter {

	/**
	 * Does this adapter understand this advice object?
	 * Is it valid to invoke the {@code getInterceptors} method with an Advisor that contains this advice as an argument?
	 *
	 * 判断当前适配器是否支持该advice（有点像功能残缺的Pointcut），用于AdvisorAdapter区分不同的Advice，以完成适配任务。
	 *
	 * @param advice an Advice such as a BeforeAdvice
	 * @return whether this adapter understands the given advice object
	 * @see #getInterceptor(org.springframework.aop.Advisor)
	 * @see org.springframework.aop.BeforeAdvice
	 */
	boolean supportsAdvice(Advice advice);

	/**
	 * 根据Advisor返回拦截器，看起来像是从Advice中提取advice，并封装为MethodInterceptor再返回。
	 *
	 * Return an AOP Alliance MethodInterceptor exposing the behavior of
	 * the given advice to an interception-based AOP framework.
	 *
	 * 似乎抛弃了Pointcut信息？Don't worry，spring框架会负责对Pointcut进行检查。
	 *
	 * <p>Don't worry about any Pointcut contained in the Advisor;
	 * the AOP framework will take care of checking the pointcut.
	 *
	 * @param advisor the Advisor. The supportsAdvice() method must have
	 *                returned true on this object
	 * @return an AOP Alliance interceptor for this Advisor. There's
	 * no need to cache instances for efficiency, as the AOP framework
	 * caches advice chains.
	 */
	MethodInterceptor getInterceptor(Advisor advisor);

}
