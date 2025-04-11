import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
    
public class StringUtilsTest{ 
    
    public static final String EMPTY = "";

    public static String leftPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(padChar, pads).concat(str);
    }


    public static String repeat(final char ch, final int repeat) {
        if (repeat <= 0) {
            return EMPTY;
        }
        final char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }


    @Test
    public void testleftPad0(){
        String result = leftPad("aaa",-1,'r');
        assertEquals("aaa", result);
    }

    @Test
    public void testleftPadlessthan0(){
        String result = leftPad("aaa",0,'r');
        assertEquals("aaa", result);
    }

    @Test
    public void testleftPadgreaterthan0(){
        String result = leftPad("aaa",1,'r');
        assertEquals("aaa", result);
    }

    @Test
    public void testrepeat0(){
        String result = repeat('r',-1);
        assertEquals("", result);
    }

    @Test
    public void testrepeatlessthan0(){
        String result = repeat('r',0);
        assertEquals("", result);
    }

    @Test
    public void testrepeatgreaterthan0(){
        String result = repeat('r',1);
        assertEquals("r", result);
    }
}
