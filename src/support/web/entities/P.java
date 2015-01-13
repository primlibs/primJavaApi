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
public class P extends WebEnt implements AbsEnt {

  public P() {
    super(EnumAttrType.id,EnumAttrType.style,EnumAttrType.title);
  }

  @Override
  public String render() {
    String result = "<p";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += escapeHtml(data.getValue());
    result += "</p>";

    return result;
  }
}
