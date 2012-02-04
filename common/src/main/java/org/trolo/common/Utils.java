package org.trolo.common;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Bytes;

/**
 * @author: Stanislav Kurilin
 */
public class Utils {
    public static String asString(ImmutableList<Byte> value) {
        return new String(Bytes.toArray(value), Charsets.UTF_8);
    }
}
