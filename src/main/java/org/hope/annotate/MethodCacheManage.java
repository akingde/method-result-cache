package org.hope.annotate;

import com.sun.deploy.util.StringUtils;
import org.util.SHA1;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hope on 17/11/5.
 */
public class MethodCacheManage {

    private MethodCacheManage() {
    }
    private static volatile MethodCacheManage instance;

    public static MethodCacheManage single() {
        if (instance == null) {
            synchronized (MethodCacheManage.class) {
                if (instance == null) {
                    instance = new MethodCacheManage();
                }
            }
        }
        return instance;
    }


    private Map<String, Map<String, MethodCache>> cacheConfig = new ConcurrentHashMap<String, Map<String, MethodCache>>();

    public String buildCacheKey(Class<?> targetClz, String methodName, Object[] args) {
        String argsKey = "";
        if (args != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(String.valueOf(args[i]).replaceAll(",", "\\\\,"));
            }
            argsKey = sb.toString().substring(0, sb.toString().length() - 1);
        }
        String wholeKey = StringUtils.join(java.util.Arrays.asList(targetClz.getName(), methodName, argsKey), ".");
        String sigKey = StringUtils.join(
                java.util.Arrays.asList( targetClz.getSimpleName(), methodName, SHA1.encryptHex(wholeKey) ), ".");
        return sigKey;
    }


    public MethodCache findMethodCache(Class<?> targetClz, Method targetMethod) {
        Map<String, MethodCache> classCacheConfig = cacheConfig.get(targetClz.getName());
        if (classCacheConfig == null) {
            classCacheConfig = new HashMap<String, MethodCache>();
            Method[] methods = targetClz.getMethods();
            for (Method method : methods) {
                MethodCache sc = method.getAnnotation(MethodCache.class);
                if (sc != null) {
                    classCacheConfig.put(this.methodSignature(method.getName(), method.getParameterTypes()), sc);
                }
            }
            cacheConfig.put(targetClz.getName(), classCacheConfig);
        }
        return classCacheConfig.get(this.methodSignature(targetMethod.getName(), targetMethod.getParameterTypes()));
    }


    private String methodSignature(String method, Object[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("(");
        if(args != null && args.length > 0){
            for (int size = args.length, i = 0; i < size; i++) {
                appendType(sb, (args[i] instanceof Class)? (Class<?>)args[i] : args[i].getClass());
                if (i < size - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private void appendType(StringBuilder sb, Class<?> type) {
        if (type.isArray()) {
            appendType(sb, type.getComponentType());
            sb.append("[]");
        }
        else {
            sb.append(type.getName());
        }
    }
}
