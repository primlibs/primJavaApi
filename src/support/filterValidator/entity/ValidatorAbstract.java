package support.filterValidator.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import support.StringAdapter;
import support.enums.ValidatorTypes;

/**
 * базовый класс для всех классов фильтров и валидаторов.
 *
 * @author Pavel Rice
 */
public abstract class ValidatorAbstract implements Cloneable, Serializable {

  /**
   * код ошибки
   */
  private String errorCode;
  /**
   * сообщение об ошибке
   */
  protected String errorMessage;
  /**
   * пользовательское об ошибке, которое замещает стандартное сообщение
   */
  private String customErrorMessage;
  /**
   * данные после фильтрации
   */
  protected Object data;
  /**
   * прерывать ли цепочку фильтров/валидаторов
   */
  private boolean terminate;
  /**
   * название пакета, в котором находится класс
   */
  static final long serialVersionUID = 12345L;
  
  

  /**
   * Выполнить валидацию или фильтрацию
   *
   * @return результат - валидно ли значение
   */
  public abstract boolean execute();

  /**
   * возвращает объект валидатора/фильтра по его имени
   *
   * @param validatorName - имя валидатора/фильтра
   * @return
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public static ValidatorAbstract getValidator(ValidatorTypes type) throws Exception {
    try {
     if(type.equals(ValidatorTypes.DATEFORMATVALIDATOR)){
         return new DateFormatValidator();
     }else if(type.equals(ValidatorTypes.DATETOFORMATFILTER)){
         return new DateToFormatFilter();
     }else if(type.equals(ValidatorTypes.DECIMALFILTER)){
         return new DecimalFilter();
     }else if(type.equals(ValidatorTypes.DECIMALVALIDATOR)){
         return new DecimalValidator();
     }else if(type.equals(ValidatorTypes.NOTNULLSTRINGVALIDATOR)){
         return new NotNullStringValidator();
     }else if(type.equals(ValidatorTypes.DIGITSFILTER)){
         return new DigitsFilter();
     }else if(type.equals(ValidatorTypes.DIGITSVALIDATOR)){
         return new DigitsValidator();
     }else if(type.equals(ValidatorTypes.PERCENTVALIDATOR)){
         return new PercentValidator();
     }else if(type.equals(ValidatorTypes.PHONEFILTER)){
         return new PhoneFilter();
     }else{
         throw new Exception ("validator name "+type+" not supported yet");
     }
    } catch (Exception e) {
      throw new Exception(StringAdapter.getStackTraceException(e));
    }
  }
  
  

  @Override
  public ValidatorAbstract clone() throws CloneNotSupportedException {
    return (ValidatorAbstract) super.clone();
  }

  /**
   * присваивает значение параметру валидатора/фильтра
   *
   * @param paramName - имя параметра
   * @param parameter - значение параметра
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   */
  final public void setParameter(String paramName, Object parameter)
          throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
    paramName = StringAdapter.ucFirst(paramName);
    Method method = this.getClass().getDeclaredMethod("set" + paramName, Class.forName("java.lang.Object"));
    method.invoke(this, parameter);
  }

  /**
   * возвращает массив всех параметров
   *
   * @return
   */
  final public HashMap<String, Object> getParameters() throws Exception {
    HashMap<String, Object> params = new HashMap<String, Object>();
    // получить массив методов
    Method[] methods = this.getClass().getDeclaredMethods();
    for (Method method : methods) {
      String methodName = method.getName();
      if (methodName.substring(0, 3).equals("get")) {
        String paramName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
        Object paramValue = method.invoke(this);
        params.put(paramName, paramValue);
      }
    }
    return params;
  }

  final public Object getParameter(String name) throws Exception {
    return getParameters().get(name);
  }

  final public void setErrorCode(String errorMessage) {
    this.errorCode = errorCode;
  }

  final public String getErrorCode() {
    return errorCode;
  }

  final public void setData(Object data) {
    if(StringAdapter.NotNull(data)==true){
      this.data = data;
    }else{
      this.data= (Object)"";
    }
  }

  final public Object getData() {
    return data;
  }

  final public String getErrorMessage() {
    return errorMessage;
  }

  final public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * добавить текст к сообщению об ошибке
   *
   * @param errorMessage
   */
  final public void addErrorMessage(String errorMessage) {
    this.errorMessage = ((this.errorMessage != null) ? this.errorMessage : "");
    this.errorMessage += errorMessage;
  }

  final public boolean isTerminate() {
    return terminate;
  }

  final public void setTerminate(boolean terminate) {
    this.terminate = terminate;
  }

  final public String getCustomErrorMessage() {
    return customErrorMessage;
  }

  final public void setCustomErrorMessage(String customErrorMessage) {
    this.customErrorMessage = customErrorMessage;
  }
  
 
  
  
}
