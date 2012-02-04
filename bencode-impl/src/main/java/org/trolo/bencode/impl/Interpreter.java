package org.trolo.bencode.impl;

import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author: Stanislav Kurilin
 */
public class Interpreter {
    private final Listener listener;

    public Interpreter(Listener listener) {
        this.listener = listener;
    }

    public void doIt(String in) {
        final byte[] content = in.getBytes(Charsets.UTF_8);
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
                    listener.string(buffer);
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
                listener.onInt(parseInt(buffer));
                buffer.clear();
                inInt = false;
                continue;
            }
            if (b == b("e") && buffer.isEmpty()) {
                checkState(buffer.isEmpty());
                listener.end();
                continue;
            }
            if (b == b(":")) {
                try{
                stringLength = parseInt(buffer);
                } catch (Exception e) {
                    listener.end();
                    listener.end();
                    listener.end();
                    listener.end();
                }
                buffer.clear();
                if (stringLength == 0) {
                    listener.string(new ArrayList<Byte>());
                } else {
                    inString = true;
                }
                continue;
            }
            buffer.add(b);

        }
    }

    private long parseInt(List<Byte> buffer) {
        final String s = new String(Bytes.toArray(buffer), Charsets.UTF_8);
        return Util.INTEGER_PARSER.apply(s);
    }

    private byte b(String d) {
        final byte[] bytes = d.getBytes();
        checkState(bytes.length == 1);
        return bytes[0];
    }
}
