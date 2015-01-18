package support.filterValidator.entity;

import support.StringAdapter;

/**
 * проверка длины строки
 *
 * @author Pavel Rice
 */
public class NotNullStringValidator extends ValidatorAbstract {

  static final long serialVersionUID = 998201847350957326L;

  public boolean execute() {
    boolean result = true;
    if(StringAdapter.isNull(data)){
        result=false;
        addErrorMessage("Значение не передано");
    }
    return result;
  }

  
}
