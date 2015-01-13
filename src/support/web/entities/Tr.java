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
public class Tr extends WebEnt implements AbsEnt {

  public Tr() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.rowspan,EnumAttrType.head);
  }

  @Override
  public String render() {
    String result = "<tr";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += "</tr>";

    return result;
  }
}
