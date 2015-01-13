/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.webclient;

import support.UploadedFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * отвечает за прием запроса от Web, сохранение параметров запроса
 * @author Rice Pavel
 */
public interface WebClient {
  
  
  /**
   * возвращает массив с параметрами, которые пришли в приложение
   * @return 
   */
  public Map<String, Object> getInnerRequest();

  /**
   * 
   * @return параметр object из запроса
   */
  public String getObject();

  public void setAction(String activeAction);
  
  public void setObject(String activeObjects);
  
  /**
   * 
   * @return параметр action из запроса
   */
  public String getAction();

  /**
   * 
   * @return тип отображения
   */
  public String getRenderType();

  /**
   * 
   * @return массив загруженных файлов
   */
  public List<UploadedFile> getUploadedFile();
  
}
