package org.hope.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by hope on 17/11/4.
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface MethodCache {

    /**
     * 过期时间
     * @return
     */
    int expire() default 60;

    /**
     * 包含的keys
     * @return
     */
    String[] includeKeys() default {};

    /**
     * 是否进行同步
     * @return
     */
    boolean sync() default false;

    /**
     * 是否缓存空对象
     * @return
     */
    boolean nullPattern() default true;

}
