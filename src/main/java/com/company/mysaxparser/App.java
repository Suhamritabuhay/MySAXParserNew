package com.company.mysaxparser;

import com.company.mysaxparser.parser.Handler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance(); //создали фабрику парсеров
        SAXParser parser = factory.newSAXParser(); //создали парсер
        Handler handler = new Handler(); //создали обработчик событий
        parser.parse(new File("src\\main\\resources\\4 6019119652838312849.xml"), handler); //парсим файл обработчиком
    }
}
