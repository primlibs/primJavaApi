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
public class Href extends WebEnt implements AbsEnt {

  public Href() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.href,EnumAttrType.title,EnumAttrType.target);
  }

  @Override
  public String render() {
    String result = "<a";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += escapeHtml(data.getValue());
    result += "</a>";

    return result;
  }
}
