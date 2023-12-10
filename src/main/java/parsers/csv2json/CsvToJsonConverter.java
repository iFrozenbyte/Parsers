package parsers.csv2json;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import parsers.interfaces.Parser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class CsvToJsonConverter implements Parser {
    @Override
    public void createFile() throws IOException {
        List<Transaction> transactionList = getNewTransactions(5);// create 5 transactions
        List<String> transactionStringFields = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            transactionStringFields.add(transaction.getIdTransaction());
            transactionStringFields.add(String.valueOf(transaction.getFiscalReceiptNumber()));
            transactionStringFields.add(String.valueOf(transaction.getSum()));
            transactionStringFields.add(String.valueOf(transaction.getOperationDate()));
        }
        String[] transactionsArray = new String[transactionStringFields.size()];
        transactionStringFields.toArray(transactionsArray);
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("src/main/resources/transactions.csv"))) {
            csvWriter.writeNext(transactionsArray);
        }
    }

    @Override
    public void convert() throws IOException, CsvValidationException {
        // convert csv to json
        final String[] COLUMNMAPPING = {"idTransaction", "fiscalReceiptNumber", "sum", "operationDate"};
        String fileName = "src/main/resources/transactions.csv";
        CSVReader csvReader = new CSVReader(new FileReader(fileName));
        List<String> csvValuesList = Arrays.asList(csvReader.readNext()); // all values from CSV-file
        List<JSONObject> jsonObjectList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < csvValuesList.size(); i = i + 4) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(COLUMNMAPPING[0], csvValuesList.get(i));
            jsonObject.put(COLUMNMAPPING[1], csvValuesList.get(i + 1));
            jsonObject.put(COLUMNMAPPING[2], csvValuesList.get(i + 2));
            jsonObject.put(COLUMNMAPPING[3], csvValuesList.get(i + 3));
            jsonObjectList.add(jsonObject);
        }
        jsonArray.addAll(jsonObjectList);
        try(FileWriter fileWriter = new FileWriter("src/main/resources/transaction.json")) {
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
        }
    }

    private List<Transaction> getNewTransactions(int transactionQuantity) {
        List<Transaction> transactions = new ArrayList<>(transactionQuantity);
        for (int i = 0; i < transactionQuantity; i++) {
            transactions.add(new Transaction(String.valueOf(UUID.randomUUID()), // unique transaction ID
                    (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L, // 10-digit fiscal receipt number
                    BigInteger.valueOf((long) (Math.random() * 999999999999999999L) + 1), // random amount in kopecks
                    new Date())); // transaction's date
        }
        return transactions;
    }
}
