package org.trolo.torrent;

/**
 * @author: Stanislav Kurilin
 */
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
}
