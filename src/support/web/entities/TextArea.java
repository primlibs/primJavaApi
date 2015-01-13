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
public class TextArea extends WebEnt implements AbsEnt {

  public TextArea() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.cols
            ,EnumAttrType.rows,EnumAttrType.placeholder,EnumAttrType.form);
  }

  @Override
  public String render() {
    String result = "<textarea";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += escapeHtml(data.getValue());
    result += "</textarea>";

    return result;
  }
}
