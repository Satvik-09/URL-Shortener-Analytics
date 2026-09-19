package com.satvik.url_shortner;

import com.satvik.url_shortner.util.Base62;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Base62Test {
    @Test
    void EncodeandDecode(){
        long originalid = 125L;
        String encoded = Base62.encode(originalid);
        long  decoded = Base62.decode(encoded);

        System.out.print("Encoded" + ":" + encoded);
        System.out.println("Decoded : " + decoded );

        assertEquals(originalid,decoded);

    }
}
