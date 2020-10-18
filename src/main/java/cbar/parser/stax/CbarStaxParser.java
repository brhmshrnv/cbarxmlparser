package cbar.parser.stax;

import cbar.model.Valute;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CbarStaxParser {


    private static XMLInputFactory factory = null;
    private static XMLEventReader reader = null;
    private static String xml = null;
    private static String date = null;
    private static List<Valute> valuteList = null;

    public static void xmlParsing() {


        try {
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            xml = "https://www.cbar.az/currencies/" + date + ".xml";
            factory = XMLInputFactory.newFactory();
            reader = factory.createXMLEventReader(new FileInputStream(xml));

            valuteList = new ArrayList<>();
            Valute temp = null;
            boolean isValute = false;
            boolean isCode = false;
            boolean isNominal = false;
            boolean isname = false;
            boolean isValue = false;

            int counter = 0;

            while (reader.hasNext()) {

                XMLEvent event = reader.nextEvent();

                if (event.getEventType() == XMLStreamConstants.START_DOCUMENT) {
                    System.out.println("xml senedini oxumaga basladi");
                } else if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    StartElement startElement = (StartElement) event;
                    String name = startElement.toString();
                    if (name.equals("Valute")) {
                        temp = new Valute();
                        isValute = true;
                        String attr = String.valueOf(name.equals("code"));
                        temp.setCode(attr);
                        isCode = true;
                    }else if (name.equals("Nominal")) {
                        isNominal = true;
                    } else if (name.equals("name")) {
                        isname = true;
                    } else if (name.equals("Value")) {
                        isValue = true;
                    }

                } else if (event.getEventType() == XMLStreamConstants.CHARACTERS) {

                    Characters characters = (Characters) event;
                    if (isNominal) {
                        temp.setNominal(characters.getData());
                    } else if (isname) {
                        temp.setName(characters.getData());
                    } else if (isValue) {
                        temp.setValue(new BigDecimal(characters.getData()));
                    }

                }else if (event.getEventType() == XMLStreamConstants.END_ELEMENT){
                    EndElement endElement = (EndElement) event;
                    String name = endElement.getName().toString();
                    if (name.equals("Valute")) {
                        valuteList.add(temp);
                        counter++;
                        temp =null;
                        isValute = false;
                        isCode =false;
                    } else if (name.equals("Nominal")) {
                        isNominal = false;
                    } else if (name.equals("name")) {
                        isname = false;
                    } else if (name.equals("Value")) {
                        isValue = false;
                    }
                    
                }



            }
            System.out.println("iscilerin siyahisi");
            valuteList.forEach(System.out::println);
        } catch (FileNotFoundException | XMLStreamException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }


    }
}
