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
public class Span extends WebEnt implements AbsEnt {

  public Span() {
    super(EnumAttrType.id,EnumAttrType.style,EnumAttrType.title);
  }

  @Override
  public String render() {
    String result = "<span";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += escapeHtml(data.getValue());
    result += "</span>";

    return result;
  }
}
