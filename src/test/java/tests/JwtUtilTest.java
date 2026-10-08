package tests;

import com.satvik.url_shortner.util.JwtUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil = new JwtUtil();

    @Test
        void shouldGenerateandValidateToekn(){
        String token = jwtUtil.generateToken("satvik");

        assertNotNull(token);
        assertTrue(jwtUtil.isTokenValid(token));
        assertEquals("satvik",jwtUtil.extractusername(token));
    }

    @Test
    void shouldRejectTamperedToken(){
        String token = jwtUtil.generateToken("satvik");
        String tampered = token.substring(0,token.length() - 5) + "XXXXX";

        assertFalse(jwtUtil.isTokenValid(tampered));
    }
}
