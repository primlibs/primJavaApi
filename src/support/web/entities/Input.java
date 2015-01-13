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
public class Input extends WebEnt implements AbsEnt {

  public Input() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.maxlength
            ,EnumAttrType.size,EnumAttrType.type,EnumAttrType.src,EnumAttrType.width,EnumAttrType.title,EnumAttrType.placeholder,EnumAttrType.form);
  }

  @Override
  public String render() {
    String result = "<input";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += " value=\"" + escapeHtml(data.getValue()) + "\" ";
    result += "/>";

    return result;
  }
}
