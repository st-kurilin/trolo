package org.trolo.bencode.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Bytes;
import org.trolo.bencode.api.Bencode;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newLinkedList;
import static org.trolo.bencode.api.Bencodes.*;

/**
 * @author: Stanislav Kurilin
 */
public class RecordedListener implements Listener {
    LinkedList<Builder> builders = newLinkedList();
    ImmutableList.Builder<Bencode> result = ImmutableList.builder();

    @Override
    public void dictStart() {
        builders.add(new MapBuilder());
    }

    @Override
    public void listStart() {
        builders.add(new ListBuilder());
    }

    @Override
    public void end() {
        if (!builders.isEmpty()) {
            final Builder current = builders.removeLast();
            add(current.build());
        }else {
            System.out.println("fuck");
        }
    }

    private void add(Bencode c) {
        if (builders.isEmpty()) {
            result.add(c);
        } else {
            builders.get(builders.size() - 1).add(c);
        }

    }

    @Override
    public void string(List<Byte> buffer) {
        add(literal(new String(Bytes.toArray(buffer), Charsets.UTF_8)));
    }

    @Override
    public void onInt(long l) {
        add(digit(l));
    }

    public Optional<ImmutableList<Bencode>> result() {
        return Optional.of(result.build());
    }

    private static interface Builder {
        void add(Bencode b);

        Bencode build();
    }

    private static class ListBuilder implements Builder {
        ImmutableList.Builder<Bencode> b = ImmutableList.builder();

        public void add(Bencode c) {
            b.add(c);
        }

        @Override
        public Bencode build() {
            return sequence(b.build());
        }
    }

    private static class MapBuilder implements Builder {
        ImmutableMap.Builder<String, Bencode> b = ImmutableMap.builder();
        String key;

        public void add(Bencode c) {
            if (key == null) {
                key = checkNotNull(c.accept(new Bencode.AbstractVisitor<String>() {
                    @Override
                    public String visitLiteral(String value) {
                        return value;
                    }
                }));
            } else {
                b.put(key, c);
                key = null;
            }
        }

        @Override
        public Bencode build() {
            return dictionary(b.build());
        }
    }
}
