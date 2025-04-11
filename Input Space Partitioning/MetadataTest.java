/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

import org.apache.tika.TikaTest;
import org.apache.tika.utils.DateUtils;

public class MetadataTest{

    public boolean equals(Object o) {

        if (!(o instanceof Metadata)) {
            return false;
        }

        Metadata other = (Metadata) o;

        if (other.size() != size()) {
            return false;
        }

        String[] names = names();
        for (String name : names) {
            String[] otherValues = other._getValues(name);
            String[] thisValues = _getValues(name);
            if (otherValues.length != thisValues.length) {
                return false;
            }
            for (int j = 0; j < otherValues.length; j++) {
                if (!otherValues[j].equals(thisValues[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void testEqualsNull(){
        boolean result = equals(null);
        assertFalse(result);
    }

    @Test
    public void testEqualsObject(){
        Metadata o1 = new Metadata();
        boolean result = equals(o1);
        assertFalse(result);
    }



    private String[] appendValues(String[] values, final String value) {
        if (value == null) {
            return values;
        }
        String[] newValues = new String[values.length + 1];
        System.arraycopy(values, 0, newValues, 0, values.length);
        newValues[newValues.length - 1] = value;
        return newValues;
    }

    @Test(expected = NullPointerException.class)
    public void testappendValuesNUll(){
        String[] result = appendValues(null,"aaa");
    }

    @Test
    public void testappendValuesEmpty(){
        String[] temp1 = {};
        String[] result = appendValues(temp1,"aaa");
        assertEquals("aaa", result[0]);
    }

    @Test
    public void testappendValuesNotEmpty(){
        String[] temp2 = {"bbb"};
        String[] result = appendValues(temp2,"aaa");
        assertEquals("bbb", result[0]);
        assertEquals("aaa", result[1]);
    }

}