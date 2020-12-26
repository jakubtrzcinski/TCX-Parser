package pl.jakubtrzcinski.tcxparser;

import com.garmin.xmlschemas.trainingcenterdatabase.v2.TrainingCenterDatabaseT;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/**
 * @author Jakub Trzcinski kuba@valueadd.pl
 * @since 26-12-2020
 */
public class TcxParser {

    private final Unmarshaller jaxbUnmarshaller;

    public TcxParser() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TrainingCenterDatabaseT.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    /**
     * Parses a stream containing TCX data
     *
     * @param stream the input stream
     * @return {@link TrainingCenterDatabaseT} object containing parsed data
     * @throws Exception when TCX file is invalid
     */
    public TrainingCenterDatabaseT parseTCX(InputStream stream) throws Exception {
        try {
            Source source = new StreamSource(stream);
            JAXBElement<TrainingCenterDatabaseT> root = jaxbUnmarshaller.unmarshal(source, TrainingCenterDatabaseT.class);
            return root.getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Not a valid TCX file.", e);
        }
    }

}
