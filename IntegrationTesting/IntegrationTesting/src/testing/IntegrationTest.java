package testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.io.TikaInputStream;
import org.junit.Test;
import org.xml.sax.ContentHandler;

public class IntegrationTest {

    private File getTestFile(String filename) throws Exception {
        URL resource = getClass().getClassLoader().getResource(filename);
        //System.out.println("Classpath: " + System.getProperty("java.class.path"));
        assertNotNull("Test file not found: " + filename, resource);
        return new File(resource.toURI());
    }

    @Test
    public void testPDFFileParsingAndMetadata() throws Exception {
        File file = getTestFile("sample2.pdf");
        try (InputStream stream = TikaInputStream.get(file)) {
            AutoDetectParser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();

            parser.parse(stream, handler, metadata);

            String content = handler.toString();
            assertNotNull("Content should not be null", content);
            assertTrue("Content should contain expected phrase", content.contains("Welcome to Apache Tika"));

            String author = metadata.get("Author");
            if (author == null) {
                author = metadata.get("dc:creator");
            }
            assertEquals("Hasan, Talha", author);
        }
    }

    @Test
    public void testDOCXFileParsing() throws Exception {
        File file = getTestFile("sample.docx");
        try (InputStream stream = TikaInputStream.get(file)) {
            AutoDetectParser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();

            parser.parse(stream, handler, metadata);

            assertNotNull(handler.toString());
            assertTrue(handler.toString().contains("Welcome to Apache Tika"));
        }
    }

    @Test
    public void testUnsupportedFileHandling() {
        try {
            File file = getTestFile("unknown.xyz");
            try (InputStream stream = TikaInputStream.get(file)) {
                AutoDetectParser parser = new AutoDetectParser();
                ContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();

                parser.parse(stream, handler, metadata);
                fail("Expected exception for unsupported format");
            }
        } catch (Exception e) {
            assertTrue(e.getMessage() != null && e.getMessage().length() > 0);
        }
    }

    @Test
    public void testHTMLFileParsingAndContentTypeDetection() throws Exception {
        File file = getTestFile("sample.html");
        try (InputStream stream = TikaInputStream.get(file)) {
            AutoDetectParser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();

            parser.parse(stream, handler, metadata);

            String contentType = metadata.get(Metadata.CONTENT_TYPE);
            assertTrue(contentType.startsWith("text/html"));
            assertTrue(handler.toString().contains("Welcome to Tika"));
        }
    }

    @Test
    public void testImageFileMetadataExtraction() throws Exception {
        File file = getTestFile("sample.jpg");
        try (InputStream stream = TikaInputStream.get(file)) {
            AutoDetectParser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();

            parser.parse(stream, handler, metadata);

            assertEquals("image/jpeg", metadata.get(Metadata.CONTENT_TYPE));
            assertNotNull(metadata.get("tiff:ImageWidth"));
            assertNotNull(metadata.get("tiff:ImageLength"));
        }
    }

    @Test
    public void testContentTypeInfluenceOnParsing() throws Exception {
        File file = getTestFile("sample2.pdf");
        try (InputStream stream = TikaInputStream.get(file)) {
            AutoDetectParser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();

            parser.parse(stream, handler, metadata);
            String contentType = metadata.get(Metadata.CONTENT_TYPE);

            assertEquals("application/pdf", contentType);
            assertTrue(handler.toString().contains("Welcome to Apache Tika"));
        }
    }

    @Test
    public void testBatchFileParsing() throws Exception {
        String[] files = {"sample.docx", "sample2.pdf", "sample.html", "sample.jpg"};
        for (String fname : files) {
            File file = getTestFile(fname);
            try (InputStream stream = TikaInputStream.get(file)) {
                AutoDetectParser parser = new AutoDetectParser();
                ContentHandler handler = new BodyContentHandler(-1);
                Metadata metadata = new Metadata();

                parser.parse(stream, handler, metadata);

                assertNotNull("Handler should contain content", handler.toString());
                assertNotNull("Metadata should be present", metadata.get(Metadata.CONTENT_TYPE));
            }
        }
    }

    @Test
    public void testAuthorMetadataConsistency() throws Exception {
        String[] files = {"sample.docx", "sample2.pdf"};
        String expectedAuthor = null;
        for (String fname : files) {
            File file = getTestFile(fname);
            try (InputStream stream = TikaInputStream.get(file)) {
                AutoDetectParser parser = new AutoDetectParser();
                ContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();

                parser.parse(stream, handler, metadata);
                String author = metadata.get("Author");
                if (author == null) author = metadata.get("dc:creator");

                if (expectedAuthor == null) {
                    expectedAuthor = author;
                } else {
                    assertEquals("Expected consistent author metadata", expectedAuthor, author);
                }
            }
        }
    }

    @Test
    public void testLargePDFFileParsing() throws Exception {
        File file = getTestFile("largesample.pdf");
        try (InputStream stream = TikaInputStream.get(file)) {
            AutoDetectParser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();

            long startTime = System.currentTimeMillis();
            parser.parse(stream, handler, metadata);
            long duration = System.currentTimeMillis() - startTime;

            assertNotNull(handler.toString());
            assertTrue("Parsing should complete under 1000ms", duration < 1000);
            System.out.println("Parsing duration: " + duration + " ms");
        }
    }
    
    @Test
    public void testCorruptedFileHandling() {
        try {
            File file = getTestFile("corrupted_sample.pdf");  // Helper method from your class
            try (InputStream stream = TikaInputStream.get(file)) {
                AutoDetectParser parser = new AutoDetectParser();
                ContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();

                parser.parse(stream, handler, metadata);
                assertTrue("Content likely empty for corrupted file", handler.toString().length() < 100);
            }
        } catch (Exception e) {
            // Expecting parsing to fail or produce very little content
            assertTrue("Expected parse exception or empty content", e.getMessage() != null);
        }
    }


}
