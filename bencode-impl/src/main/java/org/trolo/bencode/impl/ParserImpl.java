package org.trolo.bencode.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.trolo.bencode.api.Bencode;
import org.trolo.bencode.api.BencodesParser;

/**
 * @author: Stanislav Kurilin
 */
public class ParserImpl implements BencodesParser{

    @Override
    public Optional<ImmutableList<Bencode>> parse(ImmutableList<Byte> in) {
        final RecordedListener listener = new RecordedListener();
        new Interpreter(listener).doIt(in);
        return listener.result();
    }
}
