package parsers;

import com.opencsv.exceptions.CsvValidationException;
import parsers.converters.CsvToJsonConverter;
import parsers.converters.XmlToJsonConverter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class EntryPoint {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, CsvValidationException {
        CsvToJsonConverter csvToJsonConverter = new CsvToJsonConverter();
        csvToJsonConverter.createFile();
        csvToJsonConverter.convert();

        XmlToJsonConverter xmlToJsonConverter = new XmlToJsonConverter();
        xmlToJsonConverter.createFile();
        xmlToJsonConverter.convert();
    }
}