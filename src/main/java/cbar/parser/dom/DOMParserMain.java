package cbar.parser.dom;

import cbar.model.Valute;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DOMParserMain {

    public static void main(String[] args) {


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  factory.newDocumentBuilder();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String xml = "https://www.cbar.az c/currencies/" + date + ".xml";
            Document document = builder.parse(xml);

            NodeList valuteNodeList = document.getElementsByTagName("Valute");
            List<Valute> valuteList = new ArrayList<>();
            for (int i = 0; i < valuteNodeList.getLength(); i++) {
                Element valuteElement = (Element) valuteNodeList.item(i);
                String code = valuteElement.getAttribute("Code");
                String nominal = valuteElement.getElementsByTagName("Nominal").item(0).getTextContent();
                String name = valuteElement.getElementsByTagName("Name").item(0).getTextContent();
                BigDecimal value = BigDecimal.valueOf(Double.parseDouble(valuteElement.getElementsByTagName("Value").item(0).getTextContent()));

                Valute valute = new Valute(code,nominal,name,value);

                valuteList.add(valute);
                valuteList.forEach(System.out::println);
            }
            

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }
}
