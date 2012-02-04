package org.trolo.common;

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

    public String hash() {
        return value;
    }

    public static Sha1Hash create(byte[] bytes) {
        return create(new String(bytes));
    }
}
