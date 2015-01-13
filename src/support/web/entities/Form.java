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
public class Form extends WebEnt implements AbsEnt {

  public Form() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.action,EnumAttrType.method
            ,EnumAttrType.target,EnumAttrType.height,EnumAttrType.enctype);
    data.setAttribute(EnumAttrType.action, "/");
  }

  @Override
  public String render() {
    String result = "<form";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += escapeHtml(data.getValue());
    result += "</form>";

    return result;
  }
}
