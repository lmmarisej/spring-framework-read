/*
 * Copyright 2002-2016 the original author or authors.
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

package org.aopalliance.aop;    // Spring AOP加入开源 AOP aopalliance 组织，目的在于标准化AOP的使用，促进各个AOP产品之间的可交互性

/**
 * Tag interface for Advice. Implementations can be any type of advice, such as Interceptors.
 * <p>
 * 实现了将被织入到Pointcut规定的Joinpoint处的横切逻辑。
 * <p>
 * 用来定义在连接点做什么（需要对目标对象进行增强的切面行为），为切面增强提供织入接口。
 * <p>
 * per-class类型的advice
 * 可以在目标对象类的所有实例之间共享。
 * 通常只是提供方法拦截的功能，不会为目标对象类保存任何状态或者添加新的特性。
 * <p>
 * 例如
 * Before Advice、After Returning Advice、Throws Advice、Around Advice
 * <p>
 * per-instance的advice
 * 不会在目标类所有对象实例之间共享，会为不同的实例对象保存他们各自的状态以及相关的逻辑。
 * <p>
 * 例如
 * DelegatePerTargetObjectIntroductionInterceptor
 * <p>
 * 在 Aspect 中最先声明的 Advice 拥有最高优先级。
 * <p>
 * 多个Advice匹配同一个Joinpoint，而多个advice不在同一个Aspect
 * 使用SpringIoC容器注册并使用这些Aspect
 * 多个Aspect内的advice执行顺序需要通过ordered来设置以明确。
 * 通过编程使用Aspect
 * Aspect内Advice的执行顺序完全由添加到AspectJProxyFactory的顺序来决定。
 *
 * @author Rod Johnson
 * @version $Id: Advice.java,v 1.1 2004/03/19 17:02:16 johnsonr Exp $
 */
public interface Advice {

}
