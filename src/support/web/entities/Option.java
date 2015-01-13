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
public class Option extends WebEnt implements AbsEnt {

  public Option() {
    super(EnumAttrType.id,EnumAttrType.style);
  }

  @Override
  public String render() {
    String result = "<option";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += " value=\"" + escapeHtml(data.getValue()) + "\" ";
    result += ">";
    result += renderInnerEntities();
    result += "</option>";
    return result;
  }
}
