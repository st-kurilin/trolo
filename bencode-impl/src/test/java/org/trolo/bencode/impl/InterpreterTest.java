package org.trolo.bencode.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.common.primitives.Bytes;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * @author: Stanislav Kurilin
 */
public class InterpreterTest {
    @Test()
    @Ignore
    public void s() throws IOException {
        final ImmutableList<Byte> bytes = ImmutableList.copyOf(Bytes.asList(Files.toByteArray(new File("D://simpleFile.torrent"))));
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
        inOrder.verify(listener).data(d("㪤o\u0018�\u00123c܇S\u0014\u001CD�"));
        inOrder.verify(listener, times(2)).end();
        verifyNoMoreInteractions(listener);
    }

    private ImmutableList<Byte> d(String comment) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    @Test
    public void si() throws IOException {
//        final String content = Files.toString(new File("D://simplestFile.torrent")), Charsets.UTF_8);
//        final Listener listener = mock(Listener.class);
//        new Interpreter(listener).doIt(content);
//        InOrder inOrder = inOrder(listener);
//        inOrder.verify(listener).dictStart();
//        inOrder.verify(listener).data(d("info"));
//        inOrder.verify(listener).dictStart();
//        inOrder.verify(listener).data(d("pieces"));
//        inOrder.verify(listener).data(d("㪤o\u0018�\u00123c܇S\u0014\u001CD�"));
//        inOrder.verify(listener, times(2)).end();
//        verifyNoMoreInteractions(listener);
    }
}
