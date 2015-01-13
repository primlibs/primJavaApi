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
public class Td extends WebEnt implements AbsEnt {

  public Td() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.colspan
            ,EnumAttrType.width,EnumAttrType.height, EnumAttrType.rowspan, EnumAttrType.title);
  }

  @Override
  public String render() {
    String result = "<td";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += escapeHtml(data.getValue());
    result += "</td>";

    return result;
  }
}
