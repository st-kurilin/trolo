package org.trolo.simple;

import com.google.common.collect.ImmutableList;

import java.net.URI;

/**
 * @author Stanislav Kurilin
 */
class UriBuilder {
    private final URI base;

    public UriBuilder(URI base) {
        this.base = base;
    }


    public UriBuilder add(String key, ImmutableList<Byte> value) {
        return this;
    }

    public UriBuilder add(String port, String s) {
        return this;
    }

    public UriBuilder add(String port, int i) {
        return this;
    }

    public URI build() {
        return null;
    }
}
