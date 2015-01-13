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
public class Img extends WebEnt implements AbsEnt {

  public Img() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.height
            ,EnumAttrType.width,EnumAttrType.title,EnumAttrType.src);
  }

  @Override
  public String render() {
    String result = "<img";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += "/>";
    return result;
  }
}
