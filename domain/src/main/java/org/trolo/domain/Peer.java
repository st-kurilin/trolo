package org.trolo.domain;

import com.google.common.base.Optional;
import org.trolo.common.ConnectionId;

/**
 * @author Stanislav Kurilin
 */
public interface Peer {
    /**
     * @return peer's self-selected ID, as described above for the tracker request
     */
    Optional<String> peerId();

    /**
     * ip: peer's IP address either IPv6 (hexed) or IPv4 (dotted quad) or DNS name (string)
     * port: peer's port number (integer)
     *
     * @return address
     */
    ConnectionId peerAddress();
}
