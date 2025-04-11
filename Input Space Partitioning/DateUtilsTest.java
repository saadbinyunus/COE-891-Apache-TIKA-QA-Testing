package org.apache.tika.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
    
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class DateUtilsTest{
    public Date tryToParse(String dateString) {
        // Java doesn't like timezones in the form ss+hh:mm
        // It only likes the hhmm form, without the colon
        int n = dateString.length();
        if (dateString.charAt(n - 3) == ':' &&
                (dateString.charAt(n - 6) == '+' || dateString.charAt(n - 6) == '-')) {
            dateString = dateString.substring(0, n - 3) + dateString.substring(n - 2);
        }

        for (DateFormat df : iso8601InputFormats) {
            try {
                return df.parse(dateString);
            } catch (java.text.ParseException e) {
                //swallow
            }
        }
        return null;
    }

    @Test(expected = NullPointerException.class)
    public void testtryToParseNUll(){
        Date result = tryToParse(null);
    }

    @Test
    public void testtryToParseEmpty(){
        Date result = tryToParse("");
        assertEquals(null, result);
    }

    @Test
    public void testtryToParseNotEmpty(){
        Date result = tryToParse("aaa");
        assertEquals(null, result);
    }




    private static DateFormat createDateFormat(String format, TimeZone timezone) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, new DateFormatSymbols(Locale.US));
        if (timezone != null) {
            sdf.setTimeZone(timezone);
        }
        return sdf;
    }

    @Test(expected = NullPointerException.class)
    public void testcreateDateFormatNUll(){
        TimeZone UTC = TimeZone.getTimeZone("UTC");
        DateFormat result = createDateFormat(null,UTC);
    }

    @Test
    public void testcreateDateFormatEmpty(){
        TimeZone UTC = TimeZone.getTimeZone("UTC");
        DateFormat result = createDateFormat("",UTC);
        assertEquals("", result.toPattern());
    }

    @Test
    public void testcreateDateFormatNotEmpty(){
        TimeZone UTC = TimeZone.getTimeZone("UTC");
        DateFormat result = createDateFormat("aaa",UTC);
        assertEquals("aaa", result.toPattern());
    }
    

}