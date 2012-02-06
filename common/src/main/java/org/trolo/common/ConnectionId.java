package org.trolo.common;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Iterables.transform;

/**
 * @author Stanislav Kurilin
 */
public final class ConnectionId {
    private ImmutableList<Byte> ip;
    private int port;

    private ConnectionId(ImmutableList<Byte> ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static ConnectionId valueOf(List<Byte> ip, int port) {
        final ImmutableList<Byte> safeIp = ImmutableList.copyOf(ip);
        checkArgument(safeIp.size() == 4, "IP4 supports only but %s bytes presented", safeIp.size());
        return new ConnectionId(safeIp, port);
    }

    public static ConnectionId valueOf(String ip, int port) {
        return valueOf(copyOf(transform(Splitter.on(".").split(ip), new Function<String, Byte>() {
            @Override
            public Byte apply(@Nullable String input) {
                return Byte.decode(input);
            }
        })), port);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(ConnectionId.class).add("ip", ip).add("port", port).toString();
    }
}
