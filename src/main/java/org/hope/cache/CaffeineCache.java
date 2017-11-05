package org.hope.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by hope on 17/11/5.
 */
@Component
public class CaffeineCache implements Cache,InitializingBean {

    com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = null;

    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    public Object put(String key, Object value) {
        cache.put(key, value);
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = Caffeine.newBuilder().maximumSize(1000).build();
        this.cache = cache;
    }
}
