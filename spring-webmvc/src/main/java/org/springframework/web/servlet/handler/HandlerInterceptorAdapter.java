package org.springframework.web.servlet.handler;

import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Abstract adapter class for the {@link AsyncHandlerInterceptor} interface,
 * for simplified implementation of pre-only/post-only interceptors.
 *
 * @author Juergen Hoeller
 * @since 05.12.2003
 * @deprecated as of 5.3 in favor of implementing {@link HandlerInterceptor}
 * and/or {@link AsyncHandlerInterceptor} directly.
 */
@Deprecated
public abstract class HandlerInterceptorAdapter implements AsyncHandlerInterceptor {

}
