package org.trolo.simple;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.trolo.common.ByteLists;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * @author Stanislav Kurilin
 */
class UriBuilder {
    private final URI base;
    private final ImmutableMap.Builder<String, String> queryBuilder = ImmutableMap.builder();

    public UriBuilder(URI base) {
        this.base = base;
    }


    public UriBuilder add(String key, ImmutableList<Byte> value) {
        try {
            queryBuilder.put(
                    URLEncoder.encode(key, Charsets.UTF_8.displayName()),
                    URLEncoder.encode(ByteLists.toString(value), Charsets.UTF_8.displayName()));
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public UriBuilder add(String key, String value) {
        return add(key, ByteLists.fromString(value));
    }

    public UriBuilder add(String key, long value) {
        return add(key, ByteLists.fromLong(value));
    }

    public URI build() {
        final ImmutableMap<String, String> additionalQuery = queryBuilder.build();
        final StringBuilder query = new StringBuilder();
        query.append(Optional.fromNullable(base.getQuery()).or(""));
        for (String key : additionalQuery.keySet()) {
            query.append(key).append("=").append(additionalQuery.get(key));
        }
        try {
            return new URI(base.getScheme(), base.getAuthority(), base.getPath(), query.toString(), base.getFragment());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
