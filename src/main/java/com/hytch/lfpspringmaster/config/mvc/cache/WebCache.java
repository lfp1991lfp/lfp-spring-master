package com.hytch.lfpspringmaster.config.mvc.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * web缓存控制
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebCache {
	/**
	 * The <code>cache-control</code> policies to apply to the response.
	 *
	 * @see CachePolicy
	 */
	CachePolicy[] policy() default {CachePolicy.NO_CACHE};

	/**
	 * The maximum amount of time, in seconds, that this content will be considered fresh.
	 */
	long maxAge() default 0L;

	/**
	 * The maximum amount of time, in seconds, that this content will be considered fresh
	 * only for shared caches (e.g., proxy) caches.
	 */
	long sharedMaxAge() default -1L;

	long staleIfError() default -1L;

	long staleWhileRevalidate() default -1L;
}
