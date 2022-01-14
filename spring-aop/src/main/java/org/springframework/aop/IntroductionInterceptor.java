/*
 * Copyright 2002-2007 the original author or authors.
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

package org.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * Subinterface of AOP Alliance MethodInterceptor that allows additional interfaces
 * to be implemented by the interceptor, and available via a proxy using that
 * interceptor. This is a fundamental AOP concept called <b>introduction</b>.
 *
 * <p>Introductions are often <b>mixins</b>, enabling the building of composite
 * objects that can achieve many of the goals of multiple inheritance in Java.
 *
 * @author Rod Johnson
 * @see DynamicIntroductionAdvice
 *
 * 对目标对象进行拦截并添加Introduction逻辑，可以直接实现IntroductionInterceptor，在其子类invoke中实现所有的拦截逻辑。
 *
 * 只能应用对对象级别的拦截，而不是通常advice方法级别的拦截，所以无需指定Joinpoint，只需指定目标接口类型。
 *
 * Spring中，为目标对象添加新的属性和行为必须声明相应的接口以及相应的实现。通过特定的拦截器将新的接口定义以及实现类中的逻辑附加到目标对象之上。
 */
public interface IntroductionInterceptor 	// 代表新的逻辑Introduction
		extends MethodInterceptor, 			// 通过MethodInterceptor执行新逻辑
		DynamicIntroductionAdvice {

}
