package org.trolo.common;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

/**
 * @author Stanislav Kurilin
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

    public static long toLong2(List<Byte> buffer) {
        final ImmutableList.Builder<Byte> res = ImmutableList.<Byte>builder().add((byte) 0).add((byte) 0).addAll(buffer);
        return Ints.fromByteArray(Bytes.toArray(res.build()));
    }

    public static ImmutableList<Byte> fromLong(long value) {
        return fromString(Long.toString(value));
    }

    public static ImmutableList<Byte> fromString(String val) {
        return ImmutableList.copyOf(Bytes.asList(val.getBytes(Charsets.UTF_8)));
    }

    public static ImmutableList<Byte> fromBytes(byte[] value) {
        return ImmutableList.copyOf(Bytes.asList(value));
    }
}
