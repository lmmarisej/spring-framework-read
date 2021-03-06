/*
 * Copyright 2002-2018 the original author or authors.
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

package org.aopalliance.intercept;

/**
 * Intercepts calls on an interface on its way to the target. These are nested "on top" of the target.
 *
 * <p>The user should implement the {@link #invoke(MethodInvocation)}
 * method to modify the original behavior. E.g. the following class implements a tracing interceptor
 * (traces all the calls on the intercepted method(s)):
 *
 * <pre class=code>
 * class TracingInterceptor implements MethodInterceptor {
 *   Object invoke(MethodInvocation i) throws Throwable {
 *     System.out.println("method "+i.getMethod()+" is called on "+
 *                        i.getThis()+" with args "+i.getArguments());
 *     Object ret=i.proceed();
 *     System.out.println("method "+i.getMethod()+" returns "+ret);
 *     return ret;
 *   }
 * }
 * </pre>
 *
 * @author Rod Johnson
 *
 * AspectJ 定义的通知们（增强器们），或者是自己实现的MethodBeforeAdvice、AfterReturningAdvice…(总是都是org.aopalliance.aop.Advice一个通知器)，
 * 最终都会被包装成一个org.aopalliance.intercept.MethodInterceptor，最终交给MethodInvocation（子类ReflectiveMethodInvocation去执行，它会把你所有的增强器都给执行了，这就是我们面向切面编程的核心思路过程）。
 *
 * Spring AOP 没有提供 Around Advice，而是直接采用的 aopalliance 的标准接口：MethodInterceptor。
 *
 * 相比于 AfterReturningAdvice ，提供了对方法返回值进行修改的能力。
 *
 * 场景
 * 		系统安全验证及检查、系统各处的性能检测、简单的日志记录。
 */
@FunctionalInterface
public interface MethodInterceptor extends Interceptor {		// 实现后，可以作为一个 Advice 使用

	/**
	 * Implement this method to perform extra treatments before and after the invocation. Polite
	 * implementations would certainly like to invoke {@link Joinpoint#proceed()}.
	 * @param invocation the method invocation joinpoint
	 *
	 * @return the result of the call to {@link Joinpoint#proceed()}; might be intercepted by the
	 * interceptor
	 *
	 * @throws Throwable if the interceptors or the target object throws an exception
	 *
	 * 可以在此方法里，在方法执行之前、之后做对应的处理。
	 *
	 * 对代理对象方法调用入口，可以控制对对应 Joinpoint 的拦截行为，也就提供了 around Advice 的能力。
	 */
	Object invoke(MethodInvocation invocation)	// 在advice中，对joinpoint进行操作；需要执行的时候，调用invocation.proceed()方法即可。
			throws Throwable;

}
