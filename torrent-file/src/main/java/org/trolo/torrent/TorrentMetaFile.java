package org.trolo.torrent;


import org.trolo.common.Sha1Hash;

import java.net.URI;
import java.util.List;

/**
 * @author: Stanislav Kurilin
 */
public interface TorrentMetaFile {
    URI announce();

    String name();

    int bytesInPiece();

    List<Sha1Hash> pieces();

    List<SingleFileDescription> files();

    interface SingleFileDescription {
        String path();

        int length();
    }
}