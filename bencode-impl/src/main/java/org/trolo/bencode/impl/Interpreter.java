package org.trolo.bencode.impl;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;
import static org.trolo.common.ByteLists.toLong;

/**
 * @author: Stanislav Kurilin
 */
public class Interpreter {
    private final Listener listener;

    public Interpreter(Listener listener) {
        this.listener = listener;
    }

    public void doIt(ImmutableList<Byte> content) {
        List<Byte> buffer = newArrayList();
        boolean inString = false;
        boolean inInt = false;
        long stringLength = 0;
        int i = -1;
        for (byte b : content) {
            i++;
            if (inString) {
                buffer.add(b);
                if (stringLength == buffer.size()) {
                    listener.data(ImmutableList.copyOf(buffer));
                    buffer.clear();
                    inString = false;
                }
                continue;
            }
            if (b == b("d") && buffer.isEmpty()) {
                checkState(buffer.isEmpty(), buffer);
                listener.dictStart();
                continue;
            }
            if (b == b("l") && buffer.isEmpty()) {
                checkState(buffer.isEmpty());
                listener.listStart();
                continue;
            }
            if (b == b("i") && buffer.isEmpty()) {
                checkState(buffer.isEmpty());
                inInt = true;
                continue;
            }
            if (b == b("e") && inInt) {
                listener.onInt(toLong(buffer));
                buffer.clear();
                inInt = false;
                continue;
            }
            if (b == b("e")) {
                checkState(buffer.isEmpty());
                listener.end();
                continue;
            }
            if (b == b(":")) {

                stringLength = toLong(buffer);

                buffer.clear();
                if (stringLength == 0) {
                    listener.data(ImmutableList.<Byte>of());
                } else {
                    inString = true;
                }
                continue;
            }
            buffer.add(b);

        }
    }


    private byte b(String d) {
        final byte[] bytes = d.getBytes();
        checkState(bytes.length == 1);
        return bytes[0];
    }
}
