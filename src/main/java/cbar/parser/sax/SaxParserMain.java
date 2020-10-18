package cbar.parser.sax;

import cbar.model.Valute;
import cbar.service.CurrencyService;
import cbar.service.impl.CurrencyServiceImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaxParserMain {

    public static void main(String[] args) {

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.println(date);


        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            String xml = "https://www.cbar.az/currencies/" + date + ".xml";
            ValuteContentHandler handler = new ValuteContentHandler();
            parser.parse(xml, handler);

            List<Valute> list = handler.getValuteList();

            CurrencyService service = new CurrencyServiceImpl();

            list.forEach(System.out::println);


            /*list.forEach(service::saveValute);*/
            /*service.saveBatchValutes(list);*/
            /*System.out.println(service.findById(2));*/


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }


    }
}
