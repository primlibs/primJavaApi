/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;

import java.util.EnumMap;
import support.web.AbsEnt;import support.web.EnumAttrType;
import support.web.EnumAttrType;

/**
 *
 * @author Кот
 */
class Div extends WebEnt implements AbsEnt {

  public Div() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.height,EnumAttrType.width, EnumAttrType.title);
  }

  @Override
  public String render() {
    String result = "<div";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs()+ " ";
    result += ">";
    result += renderInnerEntities();
    result += escapeHtml(data.getValue());
    result += "</div>";

    return result;
  }

}
