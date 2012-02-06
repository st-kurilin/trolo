package org.trolo.simple;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;
import com.google.common.primitives.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trolo.bencode.api.Bencode;
import org.trolo.bencode.api.BencodesParser;
import org.trolo.common.ByteLists;
import org.trolo.common.Sha1Hash;
import org.trolo.domain.Peer;
import org.trolo.domain.TorrentMetaFile;
import org.trolo.domain.Tracker;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Stanislav Kurilin
 */
public class TrackerImpl implements Tracker {
    private static Logger LOG = LoggerFactory.getLogger(TrackerImpl.class);
    private final Sha1Hash clientId;
    private final BencodesParser parser;
    private final PeerParser peerParser = new PeerParser();

    public TrackerImpl(Sha1Hash clientId, BencodesParser parser) {
        this.clientId = clientId;
        this.parser = parser;
    }

    @Override
    public Optional<ImmutableSet<Peer>> process(TorrentMetaFile metaFile) {
        final URI uri = uri(metaFile);
        LOG.debug("Process url {}", uri);
        final Optional<Bencode> response = responseDictionary(uri);
        if (!response.isPresent()) return Optional.absent();
        return Optional.of(peers(response.get()));
    }

    private ImmutableSet<Peer> peers(Bencode bencode) {
        return checkNotNull(bencode.accept(new Bencode.AbstractVisitor<ImmutableSet<Peer>>() {
            @Override
            @Nullable
            public ImmutableSet<Peer> visitDictionary(ImmutableMap<String, Bencode> value) {
                return peerParser.parse(value.get("peers")).orNull();
            }
        }), "Couldn't retrieve peers from %s", bencode);
    }


    private Optional<Bencode> responseDictionary(URI uri) {
        try {
            final byte[] response = Resources.toByteArray(uri.toURL());
            LOG.debug("Responded {}", Arrays.toString(response));
            final Optional<ImmutableList<Bencode>> parsed = parser.parse(ByteLists.fromBytes(response));
            LOG.debug("Parsed {}", parsed);
            if (parsed.isPresent() && parsed.get().size() == 1) {
                return Optional.of(parsed.get().get(0));
            }
            LOG.warn("Can not get bencode from response");
            return Optional.absent();
        } catch (IOException e) {
            LOG.warn("IO exception occurred while downloading announce: {}", Throwables.getStackTraceAsString(e));
            return Optional.absent();
        }
    }

    private URI uri(TorrentMetaFile metaFile) {
        return new UriBuilder(metaFile.announce())
                .add("info_hash", metaFile.infoHash().hash())
                .add("peer_id", Bytes.asList(peerId()))
                .add("port", 6889)
                .add("uploaded", 0)
                .add("downloaded", 0)
                .add("left", metaFile.files().get(0).length())
                .add("compact", 1)
                .add("event", Event.STARTED.toString())
                .build();
    }

    private static byte[] peerId() {
        byte snark = (((3 + 7 + 10) * (1000 - 8)) / 992) - 17;
        byte[] id = new byte[20];
        Random random = new Random();
        int i;
        for (i = 0; i < 9; i++)
            id[i] = 0;
        id[i++] = snark;
        id[i++] = snark;
        id[i++] = snark;
        while (i < 20)
            id[i++] = (byte) random.nextInt(256);


        return id;
    }
}
