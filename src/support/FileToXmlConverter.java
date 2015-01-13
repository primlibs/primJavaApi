/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author кот
 */
public class FileToXmlConverter {
    private FileToXmlConverter(){
        
    }
    
    
    public static Document getDocumentFromFile(String path) throws ParserConfigurationException, SAXException, IOException{
        File fl=new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fl);
        return document;
    }
    
    
    
}
