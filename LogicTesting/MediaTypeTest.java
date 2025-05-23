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
package org.apache.tika.mime;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MediaTypeTest {
    //equals()
    @Test
    public void testEquals_sameObject() {
        MediaType type = new MediaType("application", "pdf");
        assertTrue(type.equals(type)); // o == this
    }

    @Test
    public void testEquals_null() {
        MediaType type = new MediaType("application", "pdf");
        assertFalse(type.equals(null)); // o == null
    }

    //getParameter()
    @Test
    public void testGetParameter_existingParam() {
        Map<String, String> params = new HashMap<>();
        params.put("charset", "utf-8");
        MediaType mediaType = new MediaType("text", "html", params);

        assertEquals("utf-8", mediaType.getParameter("charset"));
    }

    @Test
    public void testGetParameter_caseInsensitive() {
        Map<String, String> params = new HashMap<>();
        params.put("charset", "utf-8");
        MediaType mediaType = new MediaType("text", "html", params);

        assertEquals("utf-8", mediaType.getParameter("CHARSET"));
    }
}
