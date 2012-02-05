package org.trolo.domain;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

/**
 * @author Stanislav Kurilin
 */
public interface Tracker {
    Optional<ImmutableSet<Peer>> process(TorrentMetaFile metaFile);
}
