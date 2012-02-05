package org.trolo.bencode.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Bytes;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.trolo.bencode.parser.Interpreter;
import org.trolo.bencode.parser.Listener;
import org.trolo.common.ByteLists;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;

/**
 * @author Stanislav Kurilin
 */
public class InterpreterTest {
    @Test
    @Ignore
    public void testInterpreterOnly() throws IOException {
        final InputStream is = this.getClass().getClassLoader().getResource("simpleFile.torrent").openStream();
        final ImmutableList<Byte> bytes = ImmutableList.copyOf(Bytes.asList(ByteStreams.toByteArray(is)));
        final Listener listener = mock(Listener.class);
        new Interpreter(listener).doIt(bytes);
        InOrder inOrder = inOrder(listener);
        inOrder.verify(listener).dictStart();
        inOrder.verify(listener).data(d("comment"));
        inOrder.verify(listener).data(d("CommentHere"));
        inOrder.verify(listener).data(d("created by"));
        inOrder.verify(listener).data(d("uTorrent/3000"));
        inOrder.verify(listener).data(d("creation date"));
        inOrder.verify(listener).onInt(1328374274);
        inOrder.verify(listener).data(d("encoding"));
        inOrder.verify(listener).data(d("UTF-8"));
        inOrder.verify(listener).data(d("info"));
        inOrder.verify(listener).dictStart();
        inOrder.verify(listener).data(d("length"));
        inOrder.verify(listener).onInt(13);
        inOrder.verify(listener).data(d("name"));
        inOrder.verify(listener).data(d("simpleFile"));
        inOrder.verify(listener).data(d("piece length"));
        inOrder.verify(listener).onInt(16384);
        inOrder.verify(listener).data(d("pieces"));
        //inOrder.verify(listener).data(of((byte) 112, (byte) 105, (byte) 101, (byte) 99, (byte) 101, (byte) 115));
        inOrder.verify(listener).data(ImmutableList.copyOf(Bytes.asList(new byte[]{112, 105, 101, 99, 101, 115})));
        inOrder.verify(listener, times(2)).end();
        verifyNoMoreInteractions(listener);
    }

    private ImmutableList<Byte> d(String comment) {
        return ByteLists.fromString(comment);
    }
}
