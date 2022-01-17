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

package org.springframework.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.core.annotation.AliasFor;

/**
 * Indicates that an annotated class is a "Controller" (e.g. a web controller).
 *
 * <p>This annotation serves as a specialization of {@link Component @Component},
 * allowing for implementation classes to be autodetected through classpath scanning.
 * It is typically used in combination with annotated handler methods based on the
 * {@link org.springframework.web.bind.annotation.RequestMapping} annotation.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 2.5
 * @see Component
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.context.annotation.ClassPathBeanDefinitionScanner
 *
 * 注解在普通的POJO上，只要在指定的包下定义用于处理web请求的handle对象，然后使用指定的注解类型标注就行。
 *
 * 基于@Controller注解的handle方法支持的返回值：
 * 		ModelAndView
 * 			视图和模型信息。
 * 		String
 * 			代表逻辑视图名。
 * 		ModelMap
 * 			只包含模型信息，没有视图信息，但是能通过set方法进行设置。
 * 		Void
 * 			没有任何返回值，视图信息需要从请求路径中提取默认值，模型数据需要其它方式进行提供。
 *
 * 	请求参数绑定
 * 		JavaBean
 * 			默认采用JavaBean的getter/setter进行数据绑定，通过默认注册的PropertyEditor来完成常见数据的绑定 {@link PropertyEditorRegistrySupport#createDefaultEditors)}。
 * 		@RequestParam
 * 			明确地指定绑定关系。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

}
