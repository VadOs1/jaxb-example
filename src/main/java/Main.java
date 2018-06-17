import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws JAXBException, UnsupportedEncodingException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        File file = new File("file.xml");

        Catalog catalog = new Catalog();
        catalog.add(new Book("Author1", "Title1", 14.55, new Date()));
        catalog.add(new Book("Author2", "Title2", 66, new Date()));

        writeToFile(catalog, file, marshaller);
        readFromFile(file, unmarshaller);
        readFromString(unmarshaller);
    }

    private static void writeToFile(Catalog catalog, File file, Marshaller marshaller) throws JAXBException {
        // to make xml formatted
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // write to file
        marshaller.marshal(catalog, file);
        // print to console
        System.out.println("PRINTING BEFORE SAVING TO FILE");
        marshaller.marshal(catalog, System.out);
    }

    private static void readFromFile(File file, Unmarshaller unmarshaller) throws JAXBException {
        Catalog catalogFromFile = (Catalog) unmarshaller.unmarshal(file);
        System.out.println("PRINTING AFTER READING FROM FILE");
        System.out.println(catalogFromFile);
    }

    private static void readFromString(Unmarshaller unmarshaller) throws UnsupportedEncodingException, JAXBException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<catalog><book><author>Author1</author><price>14.55</price><publish_date>" +
                "2018-06-17T02:02:00.338-04:00</publish_date><title>Title1</title></book><book>" +
                "<author>Author2</author><price>66.0</price><publish_date>2018-06-17T02:02:00.338-04:00" +
                "</publish_date><title>Title2</title></book></catalog>";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        Catalog catalogFromString = (Catalog) unmarshaller.unmarshal(byteArrayInputStream);
        System.out.println("PRINTING AFTER READING FROM STRING");
        System.out.println(catalogFromString);
    }
}