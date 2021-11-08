package com.company.mysaxparser.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static com.company.mysaxparser.consts.Constants.*;

public class Handler extends DefaultHandler {
    private boolean isFile;
    private String node;

    private StringBuilder builder = new StringBuilder();

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        if (INCLUDE_NODE.equals(qName)) { //если дошли до <child>
            isFile = Boolean.parseBoolean(attr.getValue(IS_FILE)); //записали, что там в child'e - false или true
        }                                                  //т.е. конечная это папка или в ней еще папки
        node = qName; //запомнили путь до child'a
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String result = new String(ch, start, length).trim(); //записали в строку название файла (текст внутри элемента)
        if (!result.isBlank()) { //если строка не пустая
            if (isFile) {    //если child is-file="true" (содержит только файл)
                System.out.print(builder + SPLIT_DIR + result); //вывели весь путь + название файла
            } else { //если папка содержит папки и файлы
                if (ACTIVE_NODE.equals(node)) {  //если дошли до <name> директории
                    if (!SPLIT_DIR.equals(result)) {       //и строка не равна "/"
                        builder.append(SPLIT_DIR).append(result); //надстраиваем путь через "/" и имя файла
                    }
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (INCLUDE_NODE.equals(qName)) {    //если дошли до <child>
            if (isFile) {  //если  <child is-file="true"> - добрались до конечного файла на дне папки
                System.out.println(); //перевод в выводе на новую стоку (для описания новой ветки)
                isFile = false;  //поставили флаг на false
            } else  { //если в папке есть еще папки
                int index = builder.lastIndexOf(SPLIT_DIR);  //определяем индекс символа "/"
                if (index != -1) { //если не вышли за начало построенной строки
                    builder.delete(index, builder.length()); //укорачиваем  builder до предыдущего "/"
                }    //чтобы в тот же путь помещать имена других вложенных файлов
            }
        }
    }
}

//uri - пространство имен. В xml это ns:
//lName (localName) - имя элемента без префикса
//qName - имя элемента или имя элемента с префиксом
//uri и localName всегда пустые, если мы не подключили к фабрике обработку пространств


