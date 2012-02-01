package org.trolo.torrent;

import com.google.common.base.Optional;

/**
 * @author: Stanislav Kurilin
 */
public interface TorrentFileParser {
    Optional<TorrentMetaFile> parse(String in);
}
