package org.hope.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hope.annotate.MethodCache;
import org.hope.annotate.MethodCacheManage;
import org.hope.cache.Cache;


/**
 * Created by hope on 17/11/5.
 */
@Aspect
public class MethodCacheAnnotationAspect {

    private Cache serviceCache;

    private Object syncLock = new Object();

    @Around("@annotation(org.hope.annotate.MethodCache)")
    private Object cacheProcess(ProceedingJoinPoint jp) throws Throwable {
        Class<?> targetClz = jp.getTarget().getClass();
        String methodName = jp.getSignature().getName();
        if(!(jp.getSignature() instanceof MethodSignature)){
            return jp.proceed();
        }

        MethodSignature methodSign = (MethodSignature)jp.getSignature();
        MethodCache sc = MethodCacheManage.single().findMethodCache(targetClz, methodSign.getMethod());
        if (sc == null)
            return jp.proceed();

        String cacheKey = MethodCacheManage.single().buildCacheKey(targetClz, methodName, jp.getArgs());
        Object rval = null;
        if (sc.sync()) {// 判断是否进行同步操作
            synchronized (syncLock) {
                rval = cacheInvoke(jp, cacheKey);
            }
        } else {
            rval = cacheInvoke(jp, cacheKey);
        }

        return rval;
    }


    private Object cacheInvoke(ProceedingJoinPoint jp, String cacheKey) throws Throwable {
        Object rval = serviceCache.get(cacheKey);
        if (rval == null) {
            rval = jp.proceed();
            if(rval != null){
                serviceCache.put(cacheKey, rval);
            }
        }
        return rval;
    }

    public void setServiceCache(Cache serviceCache) {
        this.serviceCache = serviceCache;
    }
}
