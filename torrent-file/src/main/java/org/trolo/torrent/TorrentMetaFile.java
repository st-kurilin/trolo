package org.trolo.torrent;


import com.google.common.collect.ImmutableList;
import org.trolo.common.Sha1Hash;

import javax.annotation.concurrent.Immutable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Stanislav Kurilin
 */
@Immutable
public interface TorrentMetaFile {
    URI announce() throws URISyntaxException;

    String name();

    int bytesInPiece();

    ImmutableList<Sha1Hash> pieces();

    ImmutableList<SingleFileDescription> files();

    interface SingleFileDescription {
        String path();

        int length();
    }
}
