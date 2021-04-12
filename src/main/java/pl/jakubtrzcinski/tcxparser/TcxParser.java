package pl.jakubtrzcinski.tcxparser;

import com.garmin.xmlschemas.trainingcenterdatabase.v2.TrainingCenterDatabaseT;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Jakub Trzcinski kuba@valueadd.pl
 * @since 26-12-2020
 */
public class TcxParser {

    private final Unmarshaller jaxbUnmarshaller;

    public TcxParser() throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(TrainingCenterDatabaseT.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }


    /**
     * Parses a stream containing TCX data
     *
     * @param stream the input stream
     * @return {@link TrainingCenterDatabaseT} object containing parsed data
     * @throws Exception when TCX file is invalid
     */
    public TrainingCenterDatabaseT parseTCX(String stream) throws JAXBException {
        return parseTCX(new ByteArrayInputStream(stream.getBytes()));
    }

    /**
     * Parses a stream containing TCX data
     *
     * @param stream the input stream
     * @return {@link TrainingCenterDatabaseT} object containing parsed data
     * @throws Exception when TCX file is invalid
     */
    public TrainingCenterDatabaseT parseTCX(InputStream stream) throws JAXBException {
        Source source = new StreamSource(stream);
        JAXBElement<TrainingCenterDatabaseT> root = jaxbUnmarshaller.unmarshal(source, TrainingCenterDatabaseT.class);
        return root.getValue();
    }

}
