/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;

import support.web.AbsEnt;import support.web.EnumAttrType;
import support.web.EnumAttrType;

/**
 *
 * @author Кот
 */
public class Select extends WebEnt implements AbsEnt {

  public Select() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.size);
  }

  @Override
  public String render() {
    String result = "<select";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += "</select>";

    return result;
  }
}
