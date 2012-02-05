package org.trolo.simple;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trolo.common.Sha1Hash;
import org.trolo.domain.Peer;
import org.trolo.domain.TorrentMetaFile;
import org.trolo.domain.Tracker;

import java.net.URI;

/**
 * @author Stanislav Kurilin
 */
public class TrackerImpl implements Tracker {
    private static Logger LOG = LoggerFactory.getLogger(TrackerImpl.class);
    private final Sha1Hash clientId;

    public TrackerImpl(Sha1Hash clientId) {
        this.clientId = clientId;
    }

    @Override
    public Optional<ImmutableSet<Peer>> process(TorrentMetaFile metaFile) {
        final URI uri = new UriBuilder(metaFile.announce())
                .add("info_hash", metaFile.infoHash().hash())
                .add("peer_id", clientId.hash())
                .add("port", 6889)
                .add("uploaded", 0)
                .add("downloaded", 0)
                .add("left", metaFile.files().get(0).length())
                .add("compact", 0)
                .add("event", Event.STARTED.toString())
                .build();
        LOG.debug("Process url {}", uri);
        return Optional.absent();
    }
}
