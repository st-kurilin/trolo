package org.trolo.simple;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;
import com.google.common.primitives.Bytes;
import org.testng.annotations.Test;
import org.trolo.bencode.parser.ParserImpl;
import org.trolo.common.Sha1Hash;
import org.trolo.domain.Peer;
import org.trolo.domain.TorrentMetaFile;
import org.trolo.torrent.TorrentFileParserImpl;

import java.io.IOException;
import java.net.URL;

import static org.testng.Assert.assertTrue;

/**
 * @author Stanislav Kurilin
 */
public class TrackerTest {
    public static final TorrentFileParserImpl TORRENT_FILE_PARSER = new TorrentFileParserImpl(new ParserImpl(), null);
    public static final TrackerImpl TRACKER = new TrackerImpl(Sha1Hash.create("123"));

    @Test
    public void seedsForDebian() throws IOException {
        final String s = "http://cdimage.debian.org/debian-cd/6.0.4/sparc/bt-cd/debian-6.0.4-sparc-xfce+lxde-CD-1.iso.torrent";
        final byte[] backingArray = Resources.toByteArray(new URL(s));
        final ImmutableList<Byte> bytes = ImmutableList.copyOf(Bytes.asList(backingArray));
        final Optional<TorrentMetaFile> parsed = TORRENT_FILE_PARSER.parse(bytes);
        assertTrue(parsed.isPresent());
        final Optional<ImmutableSet<Peer>> peers = TRACKER.process(parsed.get());
    }
}
