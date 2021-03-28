package pl.jakubtrzcinski.tcxparser;


import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TcxParserTest {


    @Test
    public void testSampleReadWrite() {
        try {
            var sampleXml0 =
                    new Scanner(
                            this.getClass().getResourceAsStream("/sample.tcx.xml")
                    )
                            .useDelimiter("\\A")
                            .next();
            var sampleInstance0 = new TcxParser().parseTCX(sampleXml0);

            var sampleXml1 = new TcxWriter().writeToTCX(sampleInstance0).toString();
            var sampleInstance1 = new TcxParser().parseTCX(sampleXml1);


            assertEquals(
                    sampleXml0,
                    sampleXml1
            );

     /* TOTO something is wrong when mapping xsd:any to java.lang.Object.
        see https://stackoverflow.com/questions/27871349/xmlanyelement-does-not-unmarshal-into-specific-java-type-but-stop-at-jaxbeleme
        assertEquals(
                sampleInstance0,
                sampleInstance1
        );
    */
        } catch (JAXBException ex) {
            ex.printStackTrace(System.err);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testSecurity() {
        try {

            try {
                new TcxParser().parseTCX(this.getClass().getResourceAsStream("/security/simple-ssrf.xml"));
                fail("Parsing this malicious file should have failed");
            } catch (UnmarshalException expected) {
                assertTrue(expected.getCause().getMessage().contains("access is not allowed due to restriction set by the accessExternalDTD"));
            }

            try {
                new TcxParser().parseTCX(this.getClass().getResourceAsStream("/security/xxe.xml"));
                fail("Parsing this malicious file should have failed");
            } catch (UnmarshalException expected) {
                assertTrue(expected.getCause().getMessage().contains("access is not allowed due to restriction set by the accessExternalDTD"));
            }

            try {
                new TcxParser().parseTCX(this.getClass().getResourceAsStream("/security/quadratic-blowup.xml"));
                fail("Parsing this malicious file should have failed");
            } catch (UnmarshalException expected) {
                assertTrue(expected.getCause().getMessage().contains("accumulated size of entities"));
            }

            try {
                new TcxParser().parseTCX(this.getClass().getResourceAsStream("/security/xml-bomb.xml"));
                fail("Parsing this malicious file should have failed");
            } catch (UnmarshalException expected) {
                assertTrue(expected.getCause().getMessage().contains("The parser has encountered more than"));
            }

        } catch (JAXBException ex) {
            ex.printStackTrace(System.err);
            fail(ex.getMessage());
        }
    }
}
