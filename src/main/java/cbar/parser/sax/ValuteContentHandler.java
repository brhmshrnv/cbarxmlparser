package cbar.parser.sax;

import cbar.model.Valute;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ValuteContentHandler extends DefaultHandler {


    private boolean isValute;
    private boolean isCode;
    private boolean isNominal;
    private boolean isName;
    private boolean isValue;
    private List<Valute> valuteList;
    private Valute temp;

    public ValuteContentHandler() {
        this.valuteList=new ArrayList<>();
    }

    public List<Valute> getValuteList() {
        return valuteList;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equals("Valute")) {
            temp = new Valute();
            isValute = true;
            String attr = attributes.getValue("Code");
            temp.setCode(attr);
            isCode=true;
        }

        else if (qName.equals("Nominal")) {
            isNominal = true;
        } else if (qName.equals("Name")) {
            isName = true;
        } else if (qName.equals("Value")) {
            isValue = true;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch,start,length);

        if (isNominal) {
            temp.setNominal(data);
        }else if (isName){
            temp.setName(data);
        }
        else if (isValue){
            temp.setValue(new BigDecimal(data));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("Valute")) {
            valuteList.add(temp);
            temp =null;
            isValute = false;
            isCode =false;
        } else if (qName.equals("Nominal")) {
            isNominal = false;
        } else if (qName.equals("Name")) {
            isName = false;
        } else if (qName.equals("Value")) {
            isValue = false;
        }
    }



    @Override
    public void endDocument() throws SAXException {

    }
}
