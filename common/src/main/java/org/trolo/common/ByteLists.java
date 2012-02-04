package org.trolo.common;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

/**
 * @author: Stanislav Kurilin
 */
public class ByteLists {
    public static String toString(List<Byte> value) {
        return new String(Bytes.toArray(value), Charsets.UTF_8);
    }

    public static long toLong(List<Byte> buffer) {
        final String s = new String(Bytes.toArray(buffer), Charsets.UTF_8);
        try {
            return DecimalFormat.getIntegerInstance().parse(s).longValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
