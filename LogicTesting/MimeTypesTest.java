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

import static org.apache.tika.mime.MediaType.OCTET_STREAM;
import static org.apache.tika.mime.MediaType.TEXT_PLAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MimeTypesTest {

    private MimeTypes types;

    private MediaTypeRegistry registry;

    private MimeType binary;

    private MimeType text;

    private MimeType html;

    @BeforeEach
    public void setUp() throws MimeTypeException {
        types = new MimeTypes();
        registry = types.getMediaTypeRegistry();
        binary = types.forName("application/octet-stream");
        text = types.forName("text/plain");
        types.addAlias(text, MediaType.parse("text/x-plain"));
        html = types.forName("text/html");
        types.setSuperType(html, TEXT_PLAIN);
    }

    //forName()
    @Test
    public void testForName() throws MimeTypeException {
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = mimeTypes.forName("application/pdf");
  
        assertNotNull(mimeType);
        assertEquals("application/pdf", mimeType.getName());
    }
    
    //detect()
    @Test
    public void testDetect() throws IOException {
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        InputStream stream = new ByteArrayInputStream("%PDF-".getBytes());
        Metadata metadata = new Metadata();
  
        MediaType mediaType = mimeTypes.detect(stream, metadata);
        assertEquals("application/pdf", mediaType.toString());
    }
  

}
