package org.trolo.torrent;

import com.google.common.collect.ImmutableList;
import org.trolo.common.Parser;

import javax.annotation.concurrent.Immutable;

/**
 * @author Stanislav Kurilin
 */
@Immutable
public interface TorrentFileParser extends Parser<ImmutableList<Byte>, TorrentMetaFile> {
}
