package support.filterValidator.entity;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import support.StringAdapter;

/**
 * состоит ли поле только из цифр
 *
 * @author Pavel Rice
 */
public class fileXmlValidator extends ValidatorAbstract {

  static final long serialVersionUID = -6229871206744510731L;

  public boolean execute() {
    try{
      DocumentBuilderFactory.newInstance().newDocumentBuilder().parse((File) data);
      return true;
    }catch(Exception e){
      errorMessage=StringAdapter.getStackTraceException(e);
      return false;
    }
  }
}
