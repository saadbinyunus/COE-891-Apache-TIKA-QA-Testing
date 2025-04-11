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
package org.apache.tika.parser;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gagravarr.tika.FlacParser;
import org.gagravarr.tika.OpusParser;
import org.gagravarr.tika.VorbisParser;
import org.junit.jupiter.api.Test;
import org.xml.sax.ContentHandler;

import org.apache.tika.TikaTest;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.exception.WriteLimitReachedException;
import org.apache.tika.exception.ZeroByteFileException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.external.CompositeExternalParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;

public class AutoDetectParserTest extends TikaTest {
    // Easy to read constants for the MIME types:
    private static final String RAW = "application/octet-stream";
    private static final String EXCEL = "application/vnd.ms-excel";
    private static final String HTML = "text/html; charset=ISO-8859-1";
    private static final String PDF = "application/pdf";
    private static final String POWERPOINT = "application/vnd.ms-powerpoint";
    private static final String KEYNOTE = "application/vnd.apple.keynote";
    private static final String PAGES = "application/vnd.apple.pages";
    private static final String NUMBERS = "application/vnd.apple.numbers";
    private static final String CHM = "application/vnd.ms-htmlhelp";
    private static final String RTF = "application/rtf";
    private static final String PLAINTEXT = "text/plain; charset=ISO-8859-1";
    private static final String UTF8TEXT = "text/plain; charset=UTF-8";
    private static final String WORD = "application/msword";
    private static final String XML = "application/xml";
    private static final String RSS = "application/rss+xml";
    private static final String BMP = "image/bmp";
    private static final String GIF = "image/gif";
    private static final String JPEG = "image/jpeg";
    private static final String PNG = "image/png";
    private static final String OGG_VORBIS = "audio/vorbis";
    private static final String OGG_OPUS = "audio/opus";
    private static final String OGG_FLAC = "audio/x-oggflac";
    private static final String FLAC_NATIVE = "audio/x-flac";
    private static final String OPENOFFICE = "application/vnd.oasis.opendocument.text";
    private static final MediaType MY_MEDIA_TYPE = new MediaType("application", "x-myparser");
    private TikaConfig tika = TikaConfig.getDefaultConfig();

    @Test
    public void testParseWithValidInput() {
        AutoDetectParser parser = new AutoDetectParser();
        InputStream stream = new ByteArrayInputStream("Sample content".getBytes());
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
  
        assertDoesNotThrow(() -> parser.parse(stream, handler, metadata));
        assertNotNull(handler.toString());
    }
    @Test
    public void testSetDetector() {
        AutoDetectParser parser = new AutoDetectParser();
        Detector detector = new DefaultDetector();
  
        parser.setDetector(detector);
        assertEquals(detector, parser.getDetector());
    }
  
}
