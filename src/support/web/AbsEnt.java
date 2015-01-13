/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

/**
 *
 * @author кот
 */
public interface AbsEnt {

  /**
   * Рисует сущность
   *
   * @return
   */
  public String render();

  /**
   * Устанавливает аттрибут без значения, возвращает сущьность, к которой
   * аттрибут был учтановлен
   *
   * @return AbsEnt
   */
  public AbsEnt setSingleAttribute(EnumAttrNoValue singleAttribute);

  public AbsEnt setAttribute(String name, String value);
  
  /**
   * Удаляет аттрибут без значения, возвращает сущьность, к которой аттрибут был
   * учтановлен
   *
   * @return AbsEnt
   */
  public AbsEnt removeSingleAttribute(EnumAttrNoValue singleAttribute);

  /**
   * Устанавливает аттрибут со значением, возвращает сущьность, к которой
   * аттрибут был установлен,
   *
   * @return AbsEnt
   */
  public AbsEnt setAttribute(EnumAttrType type, String value);

  /**
   * Добавляет значение к аттрибуту, возвращает сущьность, к которой аттрибут
   * был учтановлен
   *
   * @return AbsEnt
   */
  public AbsEnt addAttribute(EnumAttrType type, String value);

  /**
   * Устанавливает значение сущьности, возвращает саму сущьность был учтановлен
   *
   * @return AbsEnt
   */
  public AbsEnt setValue(Object o);

  /**
   * Устанавливает css класс, возвращает саму сущьность был учтановлен
   *
   * @return AbsEnt
   */
  public AbsEnt setCss(Object o);

  /**
   * Устанавливает jsvascript , возвращает саму сущьность был учтановлен
   *
   * @return AbsEnt
   */
  public AbsEnt setJs(Object o);

  /**
   * Возвращает jsvascript
   *
   * @return String
   */
  public String getJs();

  /**
   * Возвращает css класс
   *
   * @return String
   */
  public String getCss();

  /**
   * Возвращает список установленных аттрибутов
   *
   * @return String
   */
  public EnumMap<EnumAttrType, String> getAttributesClone();

  /**
   * Возвращает 1 аттрибут
   *
   * @return String
   */
  public String getAttribute(EnumAttrType type);

  /**
   * Добавляет AbsEnt во внутренние
   *
   * @return AbsEnt
   */
  public AbsEnt addEnt(AbsEnt... e);

  /**
   * Добавляет AbsEnt во внутренние
   *
   * @return AbsEnt
   */
  public AbsEnt addEnt(Collection<AbsEnt> e);

  /**
   * Возвращает значения в виде строки
   *
   * @return String
   */
  public String getValue();

  /**
   * Возвращает аттрибуты со значениями в виде строки
   *
   * @return String
   */
  public String getStringAttrs();

  /**
   * Возвращает аттрибуты без значений в виде строки
   *
   * @return String
   */
  public String getStringSingleAttrs();

  /**
   * отрисовывает внутренние сущности
   *
   * @return String
   */
  public String renderInnerEntities();
  
  /**
   * показывает есть ли внутренние сущьности
   *
   * @return String
   */
  public Boolean existInnerEntities();
  
  /**
   * добавляет тег for(ввиду ограничений языка java)
   */
  
  public AbsEnt setFor(String name);
  
  /**
   * добавляет id
   */
  public AbsEnt setId(String name);
}
