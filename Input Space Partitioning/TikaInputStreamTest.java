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
package org.apache.tika.io;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.commons.io.input.TaggedInputStream;
import org.apache.commons.io.input.UnsynchronizedByteArrayInputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.Parser;
import org.apache.tika.utils.StringUtils;
    
    

public class peekTest{
       /**
     * Fills the given buffer with upcoming bytes from this stream without
     * advancing the current stream position. The buffer is filled up unless
     * the end of stream is encountered before that. This method will block
     * if not enough bytes are immediately available.
     *
     * @param buffer byte buffer
     * @return number of bytes written to the buffer
     * @throws IOException if the stream can not be read
     */
    public int peek(byte[] buffer) throws IOException {
        int n = 0;

        mark(buffer.length);

        int m = read(buffer);
        while (m != -1) {
            n += m;
            if (n < buffer.length) {
                m = read(buffer, n, buffer.length - n);
            } else {
                m = -1;
            }
        }

        reset();

        return n;
    }
   
   
   // Test Case 1: buffer == null
    @Test(expected = NullPointerException.class)
    public void testPeekNUll() throws IOException {
        int result = peek(null);
    }

    // Test Case 2: buffer length == 0
    @Test
    public void testPeekEmpty() throws IOException {
        byte[] buf2 = {};
        int result = peek(buf2);
        assertEquals(0, result);
    }

    // Test Case 3: buffer length > 0, stream has less data than buffer
    @Test
    public void testPeekNotEmpty() throws IOException {
        byte[] buf3 = {1, 2, 3};
        int result = peek(buf3);
        assertEquals(3, result);
    }

}