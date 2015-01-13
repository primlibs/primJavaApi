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
public class Th extends WebEnt implements AbsEnt {

  public Th() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.colspan);
  }

  @Override
  public String render() {
    String result = "<th";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += escapeHtml(data.getValue());
    result += "</th>";

    return result;
  }
}
