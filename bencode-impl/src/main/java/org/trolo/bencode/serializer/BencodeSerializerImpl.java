package org.trolo.bencode.serializer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.trolo.bencode.api.Bencode;
import org.trolo.bencode.api.BencodeSerializer;
import org.trolo.common.ByteLists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Ordering.natural;

/**
 * @author Stanislav Kurilin
 */
public class BencodeSerializerImpl implements BencodeSerializer {
    @Override
    public ImmutableList<Byte> serialize(Bencode bencode) {
        return checkNotNull(bencode).accept(new Bencode.Visitor<ImmutableList<Byte>>() {
            @Override
            public ImmutableList<Byte> visitDigit(long value) {
                return ImmutableList.<Byte>builder()
                        .addAll(ByteLists.fromString("i"))
                        .addAll(ByteLists.fromLong(value))
                        .addAll(ByteLists.fromString("e"))
                        .build();
            }

            @Override
            public ImmutableList<Byte> visitData(ImmutableList<Byte> value) {
                return ImmutableList.<Byte>builder()
                        .addAll(ByteLists.fromLong(value.size()))
                        .addAll(ByteLists.fromString(":"))
                        .addAll(value)
                        .build();
            }

            @Override
            public ImmutableList<Byte> visitSequence(ImmutableList<Bencode> value) {
                final ImmutableList.Builder<Byte> result = ImmutableList.builder();
                result.addAll(ByteLists.fromString("l"));
                for (Bencode each : value) {
                    result.addAll(serialize(each));
                }
                result.addAll(ByteLists.fromString("e"));
                return result.build();
            }

            @Override
            public ImmutableList<Byte> visitDictionary(ImmutableMap<String, Bencode> value) {
                final ImmutableList.Builder<Byte> result = ImmutableList.builder();
                result.addAll(ByteLists.fromString("d"));
                final List<String> keys = natural().sortedCopy(value.keySet());
                for (String each : keys) {
                    result.addAll(visitData(ByteLists.fromString(each))).addAll(serialize(value.get(each)));
                }
                result.addAll(ByteLists.fromString("e"));
                return result.build();
            }
        });
    }
}
