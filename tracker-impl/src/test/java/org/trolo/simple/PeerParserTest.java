package org.trolo.simple;

import com.google.common.primitives.Bytes;
import org.testng.annotations.Test;
import org.trolo.bencode.api.Bencodes;
import org.trolo.common.ByteLists;

/**
 * @author Stanislav Kurilin
 */
public class PeerParserTest {
    public static final PeerParser PARSER = new PeerParser();

    @Test
    public void buildConnectionId() {
        System.out.println(PeerParser.connectionId(ByteLists.fromBytes(new byte[]{88, -91, 52, -116, -22, 42})));
    }

    @Test
    public void buildConnectionId2() {
        System.out.println(PeerParser.connectionId(ByteLists.fromBytes(new byte[]{-126, -17, 18, 37, 29, 99})));
    }

    @Test
    public void parseSinglePeer() {
        System.out.println(PARSER.parse(Bencodes.literal(Bytes.asList(new byte[]{
                31, 58, 49, -104, -17, -13}))));
    }


    @Test
    public void testOnRealData() {
        PARSER.parse(Bencodes.literal(Bytes.asList(new byte[]{
                88, -91, 52, -116, -22, 42,
                -126, -17, 18, 37, 29, 99,
                31, 58, 49, -104, -17, -13,
                -49, -25, 92, 41, -56, -43,
                82, 34, 84, -15, -7, -26,
                95, -20, -20, -64, -110, 64,
                92, -64, 47, -34, 26, -31,
                -67, 26, -55, -19, -16, -54,
                -55, 67, 15, -3, -50, 109,
                94, -26, -94, 90, 26, -28,
                -83, -1, 124, 52, -69, -3,
                -55, 94, -11, -19, -104, -81,
                -78, -106, 92, -114, -117, -60,
                109, -51, -2, 46, 52, 124,
                67, -74, -26, -57, -5, -29,
                -83, -1, 124, 52, 121, 41,
                38, 101, -56, 26, -65, 86,
                //85, -52, -115, 104, -38, 61, 82, -64, 94, -15, -48, 102, -68, 32, -43, -86, -61, -30, 93, 82, 40, -128, 35, -126, -80, 77, 2, -113, 69, 112, -56, -95, -16, 112, 106, 52, 82, 95, 110, -105, 74, 89, 81, 25, 53, 42, -10, -101, 121, -40, -31, -11, -38, -64, 82, -21, -9, -38, 116, -76, 92, 45, -23, -41, 127, 90, -83, 89, 34, -12, -4, 96, -83, -1, 124, 52, 70, -100, 82, 9, 38, 21, -55, -115, 82, -13, 27, -43, -34, -23, 80, -15, -111, 33, 73, -114, -69, 23, 61, -41, 82, 40, 81, -37, 39, -125, -52, 29, -78, 84, -20, -25, -80, -38, 88, -14, -1, -17, -55, -12, 88, -78, 78, 75, -56, -43, 79, -106, 59, -44, -25, -24, 67, -65, 85, 127, 92, -103, 83, 46, 13, 96, 116, 21, 66, 31, -54, 84, -56, -43, -68, -66, 70, 104, 58, 47, -56, -41, -36, 8, 98, -127, 46, -27, 55, -27, 58, 25, -43, 108, -49, 65, 115, 96, -61, -65, -110, 33, -49, -34, 109, 86, -51, 5, 26, -23, -67, 83, -95, 28, 104, -69, -43, -118, -82, 85, -10, -63
        })));
    }
}
