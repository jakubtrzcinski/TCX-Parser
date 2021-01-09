package pl.jakubtrzcinski.tcxparser;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;

class TcxParserTest {


    @Test
    public void test() throws Exception {
        var parsed = new TcxParser().parseTCX(new FileInputStream("sample.tcx"));
        var response = new TcxWritter().writeToTCX(parsed);
        parsed = new TcxParser().parseTCX(response.toByteArray());
        System.out.println(response);
    }
}