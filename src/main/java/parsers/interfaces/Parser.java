package parsers.interfaces;

import com.opencsv.exceptions.CsvValidationException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface Parser {
    void createFile() throws IOException, ParserConfigurationException, TransformerException;
    void convert() throws IOException, CsvValidationException, SAXException, ParserConfigurationException;
}
