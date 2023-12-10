package parsers;

import com.opencsv.exceptions.CsvValidationException;
import parsers.csv2json.CsvToJsonConverter;

import java.io.IOException;

public class EntryPoint {
    public static void main(String[] args) throws IOException, CsvValidationException {
        CsvToJsonConverter csvToJsonConverter = new CsvToJsonConverter();
        csvToJsonConverter.createFile();
        csvToJsonConverter.convert();
    }
}