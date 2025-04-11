
package org.apache.tika.detect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TextStatisticsTest{
    private final int[] counts = new int[256];

    private int total = 0;

    public void addData(byte[] buffer, int offset, int length) {
        for (int i = 0; i < length; i++) {
            counts[buffer[offset + i] & 0xff]++;
            total++;
        }
    }

    @Test(expected = NullPointerException.class)
    public void testaddDataNUll(){
        addData(null,1,2);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testaddDataEmpty(){
        byte[] buf1 = {};
        addData(buf1,1,2);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testaddDataNotEmpty(){
        byte[] buf1 = {1,2};
        addData(buf1,1,2); 
    }




    private int count(int from, int to) {
        assert 0 <= from && to <= counts.length;
        int count = 0;
        for (int i = from; i < to; i++) {
            count += counts[i];
        }
        return count;
    }


    @Test(expected = AssertionError.class)
    public void testcount0(){
        int result = count(-1,3);
    }

    @Test
    public void testcountlessthan0(){
        int result = count(0,3);
        assertEquals(0, result);
    }

    @Test
    public void testcountgreaterthan0(){
        int result = count(1,3);
        assertEquals(0, result);
    }


}