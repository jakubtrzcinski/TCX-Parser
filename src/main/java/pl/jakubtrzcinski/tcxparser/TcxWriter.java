package pl.jakubtrzcinski.tcxparser;

import com.garmin.xmlschemas.trainingcenterdatabase.v2.TrainingCenterDatabaseT;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;

/**
 * @author Jakub Trzcinski kuba@valueadd.pl
 * @since 09-01-2021
 */
public class TcxWriter {


    private final Marshaller marshaller;

    public TcxWriter() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TrainingCenterDatabaseT.class);
        marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd");
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new GarminNamespacePrefixMapper());
    }

    public StringWriter writeToTCX(TrainingCenterDatabaseT tcx) throws JAXBException {
        var out = new StringWriter();
        var root = new JAXBElement<TrainingCenterDatabaseT>(new QName(
                "http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2",
                "TrainingCenterDatabase",
                ""),
                TrainingCenterDatabaseT.class, tcx);
        marshaller.marshal(root, out);
        return out;
    }

    private static class GarminNamespacePrefixMapper extends NamespacePrefixMapper {

        @Override
        public String[] getPreDeclaredNamespaceUris2() {
            return new String[]{
                    "xsi", "http://www.w3.org/2001/XMLSchema-instance",
                    "ns2", "http://www.garmin.com/xmlschemas/UserProfile/v2",
                    "ns3", "http://www.garmin.com/xmlschemas/ActivityExtension/v2",
                    "ns4", "http://www.garmin.com/xmlschemas/ProfileExtension/v1",
                    "ns5", "http://www.garmin.com/xmlschemas/ActivityGoals/v1"
            };
        }

        @Override
        public String getPreferredPrefix(String uri, String suggestion, boolean requirePrefix) {
            return suggestion;
        }
    }

}
