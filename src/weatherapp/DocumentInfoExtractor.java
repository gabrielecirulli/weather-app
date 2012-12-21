package weatherapp;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocumentInfoExtractor {

    private final DocumentBuilder builder;
    private final XPath xPath;
    private final Document XMLDocument;
    // Flags
    public static final int NUMERIC = 1;
    public static final int TEXTUAL = 2;

    public DocumentInfoExtractor(String documentURL) throws
            ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        this.builder = builderFactory.newDocumentBuilder();
        this.xPath = XPathFactory.newInstance().newXPath();


        this.XMLDocument = builder.parse(documentURL);
    }

    public String getSingleItem(String xPathSelector)
            throws XPathExpressionException {
        return this.getSingleItem(xPathSelector, DocumentInfoExtractor.TEXTUAL);
    }

    public String getSingleItem(String xPathSelector, int FLAG) throws XPathExpressionException {
        NodeList nodes = (NodeList) this.xPath.evaluate(xPathSelector,
                this.XMLDocument.getDocumentElement(),
                XPathConstants.NODESET);

        String textContent = nodes.item(0).getTextContent();

        if (textContent.trim().isEmpty() && FLAG == DocumentInfoExtractor.NUMERIC) {
            textContent = "0";
        }
        
        return textContent;
    }

    public String[] getAllItems(String xPathSelector) throws
            XPathExpressionException {
        NodeList nodes = (NodeList) this.xPath.evaluate(xPathSelector,
                this.XMLDocument.getDocumentElement(), XPathConstants.NODESET);

        int nodeListLength = nodes.getLength();
        String[] result = new String[nodeListLength];
        for (int i = 0; i < nodeListLength; i++) {
            result[i] = nodes.item(i).getTextContent();
        }
        return result;
    }
}
