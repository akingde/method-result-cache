package org.test;

import org.hope.annotate.MethodCache;

/**
 * Created by hope on 17/11/5.
 */
public class Mimi implements Reader {

    int i = 0;

    @MethodCache
    public String read(String t) {

        i++;
        return "start read"+t+i;
    }
}
