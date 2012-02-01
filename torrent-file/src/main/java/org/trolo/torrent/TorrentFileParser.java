package org.trolo.torrent;

import org.trolo.common.Parser;

import javax.annotation.concurrent.Immutable;

/**
 * @author: Stanislav Kurilin
 */
@Immutable
public interface TorrentFileParser extends Parser<TorrentMetaFile> {
}
