package org.trolo.common;

import com.google.common.collect.ImmutableList;

import javax.annotation.concurrent.Immutable;

/**
 * @author Stanislav Kurilin
 */
@Immutable
public final class Sha1Hash {
    private final String value;

    public static Sha1Hash create(String hash) {
        return new Sha1Hash(hash);
    }

    private Sha1Hash(String value) {
        this.value = value;
    }

    public ImmutableList<Byte> hash() {
        return null;
    }

    public static Sha1Hash create(byte[] bytes) {
        return create(new String(bytes));
    }
}
