package cbar.parser.jaxb;

import cbar.model.Valute;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JAXBParserMain {
    public static void main(String[] args) {

        System.out.println(LocalDateTime.now());

        try {
            JAXBContext context = JAXBContext.newInstance(JAXBValutes.class);
            Unmarshaller unmarshaller =  context.createUnmarshaller();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String xml = "https://www.cbar.az/currencies/" + date + ".xml";

            List<Valute> valutes = (List<Valute>) unmarshaller.unmarshal(new FileReader(xml));

            String cbarXml = "cbar.xml";
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(valutes,new FileWriter(xml));

            valutes.forEach(System.out::println);

        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }


    }
}
