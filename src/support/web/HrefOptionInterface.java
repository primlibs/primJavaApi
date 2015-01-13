/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web;

import support.web.webclient.WebClientImpl.RenderTypes;

/**
 *
 * @author кот
 */
public interface HrefOptionInterface {
  
  /*
   * устанавливает объект
   */
  public void setObject(String object);
  
  /*
   * устанавливает action
   */
  public void setAction(String action);
  
  
  /*
   * устанавливает title
   */
  public void setTitle(String title);
  
  /*
   * устанавливает путь к кнопке
   */
  public void setImg(String Img);
  
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
  public void setImgParams(Integer width,Integer height);
  
  /*
   * устанавливает тип рендера
   */
  public void setRenderType(RenderTypes rt);
  
  /*
   * устанавливает тип обработчика по onclick
   */
  public void setJsHandler(String jsOnclick);
  
  /*
   * показывать ли текст если нет прав на переход по ссылке
   */
  public void setShowWithoutRights(Boolean show);
  
  /*
   * установить название
   */
  public void setName(Object name);
  
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
  
  /*
   * показывать ли текст если нет прав на переход по ссылке
   */
  public boolean isShow();
  
  /*
   * получить название
   */
  public String getName();
}
