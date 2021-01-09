package pl.jakubtrzcinski.tcxparser;

import com.garmin.xmlschemas.trainingcenterdatabase.v2.ObjectFactory;
import com.garmin.xmlschemas.trainingcenterdatabase.v2.TrainingCenterDatabaseT;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Jakub Trzcinski kuba@valueadd.pl
 * @since 09-01-2021
 */
public class TcxWritter {

    private final Marshaller marshaller;

    public TcxWritter() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TrainingCenterDatabaseT.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public ByteArrayOutputStream writeToTCX(TrainingCenterDatabaseT tcx) throws Exception {
        var out = new ByteArrayOutputStream();
        var root = new JAXBElement<>(new QName("uri", "local"), TrainingCenterDatabaseT.class, tcx);
        marshaller.marshal(root, out);
        return out;
    }

}
