package org.trolo.bencode.impl;

import java.util.List;

/**
 * @author: Stanislav Kurilin
 */
public interface Listener {
    void dictStart();

    void listStart();

    void end();

    void string(List<Byte> buffer);

    void onInt(long l);
}
