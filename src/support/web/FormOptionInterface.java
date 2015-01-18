/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web;

import java.util.Map;
import support.web.webclient.WebClientImpl.RenderTypes;

/**
 *
 * @author кот
 */
public interface FormOptionInterface {
  /*
   * устанавливает тип формы горизонтальная. - вертикальная
   */

  public void setHorisontal(Boolean horizontal);

  /*
   * устанавливает объект
   */
  public void setObject(String object);

  public String getButtonCssClass();

  public void setButtonCssClass(String buttonCssClass);

  public void addButtonCssClass(String buttonCssClass);

  /*
   * устанавливает action
   */
  public void setAction(String action);

  /*
   * устанавливает specAction
   */


  /**
   *
   * @return HashMap, в котором каждому элементу соответствует ИД. Этот ИД
   * присваивается не самому элементу, а ячейке таблицы, в которой находится
   * элемент. Если форма выводится вертикально - ИД присваивается строке
   * таблицы, в которой находится элемент. Если форма выводится горизонтально -
   * ИД присваивается ячейке таблицы, в которой находится элемент.
   *
   */
  public Map<AbsEnt, String> getIdMap();

  /**
   * установить HashMap, в котором каждому элементу соответствует ИД. Этот ИД
   * присваивается не самому элементу, а ячейке таблицы, в которой находится
   * элемент. Если форма выводится вертикально - ИД присваивается строке
   * таблицы, в которой находится элемент. Если форма выводится горизонтально -
   * ИД присваивается ячейке таблицы, в которой находится элемент.
   *
   */
  public void setIdMap(Map<AbsEnt, String> idMap);

  /*
   * устанавливает title
   */
  public void setTitle(String title);

  /*
   * устанавливает путь к кнопке
   */
  public void setImg(String Img);

  /*
   * Устанавливает тип формы на загрузку файлов
   */
  public void setFormToUploadFiles(Boolean file);

  /**
   * расположение кнопки в начале формы
   * @param place 
   */
  public void setPlaceButtonAtBegin(Boolean place);
  
  /**
   * расположение кнопки в начале формы
   * @return 
   */
   public Boolean isPlaceButtonAtBegin();

  
  /*
   * Устанавливает название кнопки
   */
  public void setButtonName(String butName);

  /*
   * валидировать по доступу
   */
  public void setValidateRights();

  /*
   * не валидировать по доступу
   */
  public void setNoValidateRights();
  /*
   * уснанавливает параметры картинки - кнопки
   */

  public void setImgParams(Integer width, Integer height);

  /*
   * устанавливает тип рендера
   */
  public void setRenderType(RenderTypes rt);

  /*
   * устанавливает тип обработчика по onclick
   */
  public void setJsHandler(String jsOnclick);

  /*
   * горизонтально выводить форму или вертикально
   */
  public Boolean isHorizontal();
  /*
   * возвращает объект
   */

  public String getObject();
  /*
   * возвращает action
   */

  public String getAction();


  /*
   * возвращает title
   */
  public String getTytle();

  /*
   * возвращает путь с картинке
   */
  public String getImg();

  /*
   * возвращает работает ли форма с файлами 
   */
  public Boolean isFile();

  /*
   * возвращает название кнопки
   */
  public String getВuttonName();

  /*
   * возвращает проверять ли по правам
   */
  public Boolean isRights();

  /*
   * возвращает ширину картинки кнопки
   */
  public Integer getImgWidth();

  /*
   * возвращает высоту картинки кнопки
   */
  public Integer getImgHeight();

  /*
   * возвращает тип рендера
   */
  public RenderTypes getRenderType();

  /*
   * возвращает тип обработчика по onclick
   */
  public String getJshandler();  
}
