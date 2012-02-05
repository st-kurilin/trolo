package org.trolo.bencode.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;
import org.trolo.bencode.api.Bencode;
import org.trolo.bencode.api.BencodeSerializer;
import org.trolo.bencode.serializer.BencodeSerializerImpl;
import org.trolo.common.ByteLists;

import static com.google.common.base.Preconditions.checkArgument;
import static org.testng.Assert.assertEquals;

/**
 * @author Stanislav Kurilin
 */
public class SerializerTest {
    private final BencodeSerializer serializer = new BencodeSerializerImpl();

    @Test(dataProvider = "stringBencode", dataProviderClass = BencodeSimpleTestData.class)
    public void test(String serializedShouldBe, Optional<ImmutableList<Bencode>> toSerialize) {
        checkArgument(toSerialize.isPresent());
        checkArgument(toSerialize.get().size() == 1);
        final String serializedActual = ByteLists.toString(serializer.serialize(toSerialize.get().get(0)));
        assertEquals(serializedActual, serializedShouldBe);
    }
}
