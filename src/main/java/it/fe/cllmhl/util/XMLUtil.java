package it.fe.cllmhl.util;

import it.fe.cllmhl.core.CoreErrors;
import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XMLUtil {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(XMLUtil.class);

    public static NodeList extractNodesByXpath(Document pDocument, String pStrXpathExpression) {
        // Create a XPathFactory
        XPathFactory lXPathFactory = XPathFactory.newInstance();
        // Create a XPath object
        XPath lXPath = lXPathFactory.newXPath();
        // Compiliamo una espressione
        XPathExpression lXPathExpression;
        try {
            lXPathExpression = lXPath.compile(pStrXpathExpression);
        } catch (XPathExpressionException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());

        }
        // Run the query and get a nodeset
        NodeList lNodeList;
        try {
            lNodeList = (NodeList) lXPathExpression.evaluate(pDocument, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());

        }
        return lNodeList;
    }

    public static String extractValueByXpath(Element pElement, String pStrXpathExpression) {
        // Create a XPathFactory
        XPathFactory lXPathFactory = XPathFactory.newInstance();
        // Create a XPath object
        XPath lXPath = lXPathFactory.newXPath();
        String lStrValue;
        try {
            lStrValue = lXPath.evaluate(pStrXpathExpression, pElement);
        } catch (XPathExpressionException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());
        }
        return lStrValue;
    }

    public static Document readXmlFile(File pXmlFile) {
        DocumentBuilderFactory lDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        lDocumentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder lDocumentBuilder;
        try {
            lDocumentBuilder = lDocumentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());

        }
        Document lDocument;
        try {
            lDocument = lDocumentBuilder.parse(pXmlFile);
        } catch (SAXException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());
        } catch (IOException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());
        }
        return lDocument;
    }
    
    private XMLUtil(){}
}
