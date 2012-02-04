package org.trolo.bencode.impl;

import com.google.common.collect.ImmutableList;

/**
 * @author Stanislav Kurilin
 */
public interface Listener {
    void dictStart();

    void listStart();

    void end();

    void data(ImmutableList<Byte> val);

    void onInt(long l);
}
