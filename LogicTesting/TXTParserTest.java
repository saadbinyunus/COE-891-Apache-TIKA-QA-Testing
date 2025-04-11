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
package org.apache.tika.parser.txt;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import org.apache.tika.TikaTest;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;

public class TXTParserTest extends TikaTest {

    private Parser parser = new TXTParser();

    //parse()
    @Test
    public void testParse_validTextStream() throws Exception {
        String testText = "Hello Tika!";
        InputStream stream = new ByteArrayInputStream(testText.getBytes(StandardCharsets.UTF_8));
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
    
        TXTParser parser = new TXTParser();
        parser.parse(stream, handler, metadata, context);
    
        assertEquals("Hello Tika!", handler.toString().trim());
        assertEquals("text/plain", metadata.get(Metadata.CONTENT_TYPE));
    }
    
    //getSupportedTypes
    @Test
    public void testGetSupportedTypes_nullContext() {
        TXTParser parser = new TXTParser();
        Set<MediaType> types = parser.getSupportedTypes(null);
    
        assertTrue(types.contains(MediaType.TEXT_PLAIN));
    }

}


