package org.trolo.simple;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.trolo.bencode.api.Bencode;
import org.trolo.common.ByteLists;
import org.trolo.common.ConnectionId;
import org.trolo.common.Parser;
import org.trolo.domain.Peer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Stanislav Kurilin
 */
public class PeerParser implements Parser<Bencode, ImmutableSet<Peer>> {
    private static final int IP_SIZE = 4;
    private static final int PORT_SIZE = 2;
    private static final int SHORT_FORM_SIZE = IP_SIZE + PORT_SIZE;

    @Override
    public Optional<ImmutableSet<Peer>> parse(Bencode in) {
        return Optional.of(in.accept(new Bencode.AbstractVisitor<ImmutableSet<Peer>>() {
            @Override
            public ImmutableSet<Peer> visitData(final ImmutableList<Byte> value) {
                final ImmutableSet.Builder<Peer> builder = ImmutableSet.builder();
                checkArgument(value.size() % SHORT_FORM_SIZE == 0,
                        "Expected {} bytes per peer. But was {} bytes", SHORT_FORM_SIZE, value.size());
                for (int i = 0; i < value.size(); i += SHORT_FORM_SIZE) {
                    final ConnectionId connectionId = connectionId(value.subList(i, i + SHORT_FORM_SIZE));
                    builder.add(peer(connectionId));
                }
                return builder.build();
            }
        }));
    }

    private static Peer peer(final ConnectionId connectionId) {
        return new Peer() {
            @Override
            public Optional<String> peerId() {
                return Optional.absent();
            }

            @Override
            public ConnectionId peerAddress() {
                return connectionId;
            }

            @Override
            public String toString() {
                return Objects.toStringHelper(Peer.class)
                        .add("peerId", Optional.absent())
                        .add("connectionId", connectionId)
                        .toString();
            }
        };
    }

    public static ConnectionId connectionId(ImmutableList<Byte> bytes) {
        final ImmutableList<Byte> ip = bytes.subList(0, IP_SIZE);
        final long port = ByteLists.toLong2(bytes.subList(IP_SIZE, SHORT_FORM_SIZE));
        return ConnectionId.valueOf(ip, (int) port);
    }
}
