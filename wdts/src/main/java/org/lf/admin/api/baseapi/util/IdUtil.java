package org.lf.admin.api.baseapi.util;

import org.apache.commons.codec.binary.Base32;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by sdspb on 2017/11/23.
 */
public class IdUtil {
    public static String getShortUUID() {
        UUID uuid = UUID.randomUUID();
        Base32 base32 = new Base32();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return base32.encodeToString(bb.array()).replace("=", "");
    }
}
