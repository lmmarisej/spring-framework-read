/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.aop.aspectj;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
// 通常情况下我们需要通过 ProceedingJoinPoint 的 proceed 方法继续调用链的执行
import org.aspectj.lang.ProceedingJoinPoint;		// 对于 AroundAdvice 来说，他的第一个参数必须是 ProceedingJoinPoint，并且必须指定
import org.aspectj.weaver.tools.JoinPointMatch;

import org.springframework.aop.ProxyMethodInvocation;

/**
 * Spring AOP around advice (MethodInterceptor) that wraps
 * an AspectJ advice method. Exposes ProceedingJoinPoint.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 */
@SuppressWarnings("serial")
public class AspectJAroundAdvice extends AbstractAspectJAdvice implements MethodInterceptor, Serializable {

	public AspectJAroundAdvice(
			Method aspectJAroundAdviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aif) {

		super(aspectJAroundAdviceMethod, pointcut, aif);
	}


	@Override
	public boolean isBeforeAdvice() {
		return false;
	}

	@Override
	public boolean isAfterAdvice() {
		return false;
	}

	@Override
	protected boolean supportsProceedingJoinPoint() {
		return true;
	}

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		if (!(mi instanceof ProxyMethodInvocation)) {
			throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
		}
		ProxyMethodInvocation pmi = (ProxyMethodInvocation) mi;
		ProceedingJoinPoint pjp = lazyGetProceedingJoinPoint(pmi);
		JoinPointMatch jpm = getJoinPointMatch(pmi);
		return invokeAdviceMethod(pjp, jpm, null, null);
	}

	/**
	 * Return the ProceedingJoinPoint for the current invocation,
	 * instantiating it lazily if it hasn't been bound to the thread already.
	 * @param rmi the current Spring AOP ReflectiveMethodInvocation,
	 * which we'll use for attribute binding
	 * @return the ProceedingJoinPoint to make available to advice methods
	 */
	protected ProceedingJoinPoint lazyGetProceedingJoinPoint(ProxyMethodInvocation rmi) {
		return new MethodInvocationProceedingJoinPoint(rmi);
	}

}
