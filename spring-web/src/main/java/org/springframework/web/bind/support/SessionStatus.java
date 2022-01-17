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

package org.springframework.web.bind.support;

/**
 * Simple interface that can be injected into handler methods, allowing them to
 * signal that their session processing is complete. The handler invoker may
 * then follow up with appropriate cleanup, e.g. of session attributes which
 * have been implicitly created during this handler's processing (according to
 * the
 * {@link org.springframework.web.bind.annotation.SessionAttributes @SessionAttributes}
 * annotation).
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.web.bind.annotation.SessionAttributes
 *
 * 管理请求处理之后的session状态。
 */
public interface SessionStatus {

	/**
	 * Mark the current handler's session processing as complete, allowing for
	 * cleanup of session attributes.
	 *
	 * 在 RequestMapping 方法参数中由框架自动注入 SessionStatus，在方法中调用 setComplete 将 SessionAttributes 使用完的数据清除出 session。
	 */
	void setComplete();

	/**
	 * Return whether the current handler's session processing has been marked
	 * as complete.
	 */
	boolean isComplete();

}
