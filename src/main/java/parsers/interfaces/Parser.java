package parsers.interfaces;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;

public interface Parser {
    void createFile() throws IOException;
    void convert() throws IOException, CsvValidationException;
}
