package parsers.converters;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.*;
import parsers.businessobjects.Transaction;
import parsers.interfaces.Parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XmlToJsonConverter implements Parser {
    private final String SOURCE_PATH = "src/main/resources/XmlSource.xml";
    private final String TARGET_PATH = "src/main/resources/fromXmlToJson.json";

    @Override
    public void createFile() throws ParserConfigurationException, TransformerException {
                                // Create XML-file
        List<Transaction> transactionList = CsvToJsonConverter.getNewTransactions(3);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element transactions = doc.createElement("transactions");
        doc.appendChild(transactions);

        for (Transaction value : transactionList) {
            Element transaction = doc.createElement("transaction");
            transactions.appendChild(transaction);

            Element idTransaction = doc.createElement("id");
            idTransaction.appendChild(doc.createTextNode(value.getIdTransaction()));
            transaction.appendChild(idTransaction);

            Element fiscalReceiptNumber = doc.createElement("fiscal");
            fiscalReceiptNumber.appendChild(doc.createTextNode(String.valueOf(value.getFiscalReceiptNumber())));
            transaction.appendChild(fiscalReceiptNumber);

            Element sum = doc.createElement("sum");
            sum.appendChild(doc.createTextNode(String.valueOf(value.getSum())));
            transaction.appendChild(sum);

            Element date = doc.createElement("date");
            date.appendChild(doc.createTextNode(String.valueOf(value.getOperationDate())));
            transaction.appendChild(date);
        }

        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(SOURCE_PATH));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(domSource, streamResult);
    }

    @Override
    public void convert() throws IOException {
        // XML -> JSON парсер
        XmlMapper xmlMapper = new XmlMapper();
        List<Map<String, String>> entries = xmlMapper.readValue(new File(SOURCE_PATH), List.class);
        JSONArray jsonArray = new JSONArray();
        for (Map<String, String> entry : entries) {
            jsonArray.add(entry); // сохраняется порядок элементов в отличие от JSONObject
        }
        try(FileWriter fileWriter = new FileWriter(TARGET_PATH)) {
            fileWriter.write(jsonArray.toJSONString());
        }
    }



    //todo: метод через рекурсию проходится по всем нодам. Пока данный подход неактуален.
    private void getXmlNodes(Node node, List<Map<String, String>> xmlValues) {
        NodeList nodeList = node.getChildNodes();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            NodeList list = item.getChildNodes();
            if (Node.ELEMENT_NODE == item.getNodeType()) {

                if (list.item(1) == null) {

                    Element element = (Element) item;
                    map.clear();
                    map.put(element.getTagName(), element.getTextContent());
                    xmlValues.add(map);
                }
                getXmlNodes(item, xmlValues); // рекурсия
            }
        }
    }
}