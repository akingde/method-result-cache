package org.hope.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * Created by dongchao on 17/11/7.
 */
public class GuavaCache implements Cache,InitializingBean {

    private com.google.common.cache.Cache<Object, Object> cache = null;

    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    public Object put(String key, Object value) {
        cache.put(key, value);
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        com.google.common.cache.Cache<Object, Object> cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();
        this.cache = cache;
    }
}
