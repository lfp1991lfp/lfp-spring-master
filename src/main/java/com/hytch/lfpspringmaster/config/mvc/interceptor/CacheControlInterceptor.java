package com.hytch.lfpspringmaster.config.mvc.interceptor;

import com.hytch.lfpspringmaster.config.mvc.cache.CachePolicy;
import com.hytch.lfpspringmaster.config.mvc.cache.WebCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 缓存配置控制烂机器.
 */
@Slf4j
public class CacheControlInterceptor extends HandlerInterceptorAdapter {

	private static final String HEADER_EXPIRES = "Expires";
	private static final String HEADER_CACHE_CONTROL = "Cache-Control";

	private boolean useExpiresHeader = false;

	/**
	 * Creates a new cache control handler interceptor.
	 */
	public CacheControlInterceptor() {
		super();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		WebCache webCache = getCacheControl(request, response, handler);
		final String cache = this.createCacheControlHeader(webCache);
		if (cache != null) {
			response.setHeader(HEADER_CACHE_CONTROL, cache);
			if (useExpiresHeader) {
				response.setDateHeader(HEADER_EXPIRES, createExpiresHeader(webCache));
			}
		}
	}

	/**
	 * Returns the {@link WebCache} annotation specified for the
	 * given request, response and handler.
	 *
	 * @param request  the current <code>HttpServletRequest</code>
	 * @param response the current <code>HttpServletResponse</code>
	 * @param handler  the current request handler
	 * @return the <code>CacheControl</code> annotation specified by
	 * the given <code>handler</code> if present; <code>null</code> otherwise
	 */
	protected final WebCache getCacheControl(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final Object handler) {
		if (handler == null || !(handler instanceof HandlerMethod)) {
			return null;
		}
		final HandlerMethod handlerMethod = (HandlerMethod) handler;

		return AnnotationUtils.findAnnotation(handlerMethod.getMethod(), WebCache.class);
//		return handlerMethod.getBeanType().getAnnotation(WebCache.class);
	}

	/**
	 * Returns cache control header value from the given {@link WebCache}
	 * annotation.
	 *
	 * @param webCache the <code>WebCache</code> annotation from which to
	 *                 create the returned cache control header value
	 * @return the cache control header value
	 */
	protected final String createCacheControlHeader(final WebCache webCache) {

		final StringBuilder builder = new StringBuilder();
		if (webCache == null) {
			return null;
		}
		final CachePolicy[] policies = webCache.policy();
		for (CachePolicy policy : policies) {
			appendDirective(builder, policy.policy());
		}

		if (0 <= webCache.maxAge()) {
			this.appendDirective(builder, "max-age=" + Long.toString(webCache.maxAge()));
		}

		if (0 <= webCache.sharedMaxAge()) {
			this.appendDirective(builder, "s-maxage=" + Long.toString(webCache.sharedMaxAge()));
		}

		if (0 <= webCache.staleIfError()) {
			this.appendDirective(builder, "stale-if-error=" + Long.toString(webCache.staleIfError()));
		}

		if (0 <= webCache.staleIfError()) {
			this.appendDirective(builder, "stale-while-revalidate=" + Long.toString(webCache.staleWhileRevalidate()));
		}

		String ccHeaderValue = builder.toString();
		return StringUtils.hasText(ccHeaderValue) ? ccHeaderValue : null;
	}

	private void appendDirective(StringBuilder builder, String value) {
		if (builder.length() > 0) {
			builder.append(", ");
		}

		builder.append(value);
	}

	/**
	 * Returns an expires header value generated from the given
	 * {@link WebCache} annotation.
	 *
	 * @param webCache the <code>CacheControl</code> annotation from which to
	 *                 create the returned expires header value
	 * @return the expires header value
	 */
	private final long createExpiresHeader(final WebCache webCache) {

		final Calendar expires = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

		if (webCache.maxAge() >= 0) {
			expires.add(Calendar.SECOND, (int) webCache.maxAge());
		}

		return expires.getTime().getTime();
	}

	/**
	 * True to set an expires header when a {@link WebCache} annotation is present
	 * on a handler; false otherwise.   Defaults to true.
	 *
	 * @param useExpiresHeader <code>true</code> to set an expires header when a
	 *                         <code>CacheControl</code> annotation is present on a handler; <code>false</code> otherwise
	 */
	public final void setUseExpiresHeader(final boolean useExpiresHeader) {
		this.useExpiresHeader = useExpiresHeader;
	}
}
