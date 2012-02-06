package org.trolo.simple;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Stanislav Kurilin
 */
class UriBuilder {
    private static Logger LOG = LoggerFactory.getLogger(TrackerImpl.class);
    private final URI base;
    private final ImmutableMap.Builder<String, String> queryBuilder = ImmutableMap.builder();

    public UriBuilder(URI base) {
        this.base = base;
    }


    public UriBuilder add(String key, List<Byte> value) {
        queryBuilder.put(key, urlEncode(value));
        return this;
    }

    public UriBuilder add(String key, String value) {
        queryBuilder.put(key, value);
        return this;
    }

    public UriBuilder add(String key, long value) {
        return add(key, Long.toString(value));
    }

    public URI build() {
        final ImmutableMap<String, String> additionalQuery = queryBuilder.build();
        final StringBuilder query = new StringBuilder();
        query.append(Optional.fromNullable(base.getQuery()).or(""));
        for (String key : additionalQuery.keySet()) {
            query.append(key).append("=").append(additionalQuery.get(key)).append("&");
        }
        LOG.debug("Additional query {}", query);
        try {
            return new URI(String.format("%s://%s%s?%s", checkNotNull(base.getScheme()), checkNotNull(base.getAuthority()), checkNotNull(base.getPath()), query.toString()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static String urlEncode(List<Byte> bs) {

        //URLEncoder.encode(ByteLists.toString(bs), Charsets.UTF_8.displayName()))
        final StringBuilder sb = new StringBuilder(bs.size() * 3);
        for (byte b : bs) {
            int c = b & 0xFF;
            sb.append('%');
            if (c < 16)
                sb.append('0');
            sb.append(Integer.toHexString(c));
        }
        LOG.debug("UrlEncode in:{};out:{}", bs, sb);
        return sb.toString();
    }
}
