package org.hope.cache;

/**
 * Created by hope on 17/11/4.
 */
public interface Cache {

    /**
     * 获取缓存数据
     * @param key
     * @return
     */
    public Object get(String key);


    /**
     * 保存数据
     * @param key
     * @param value
     * @return
     */
    public Object put(String key,Object value);

}
