package support.filterValidator.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * состоит ли поле только из цифр
 *
 * @author Pavel Rice
 */
public class PercentValidator extends ValidatorAbstract {

  static final long serialVersionUID = -6229871206744510731L;

  public boolean execute() {
    // привести к строке 
    String str = data.toString();
    // проверить на соответствие регулярному выражению
    Integer dat=Integer.valueOf(str);
    boolean isValid = true;
    if(dat<0||dat>100){
        isValid=false;
    }
    if (!isValid) {
      setErrorMessage("Параметр должен принимать значения от 0 до 100");
    }
    return isValid;
  }
}
